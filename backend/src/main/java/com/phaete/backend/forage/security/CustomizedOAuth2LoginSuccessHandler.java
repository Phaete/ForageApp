package com.phaete.backend.forage.security;

import com.phaete.backend.forage.model.InvalidAuthenticationException;
import com.phaete.backend.forage.model.UserDTO;
import com.phaete.backend.forage.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Custom success handler for OAuth2 login.
 * <p>
 * Overrides the onAuthenticationSuccess Method to not only redirect the user, but also create a User in the database if
 * the oAuth user is logging in for the first time
 */
@Component
public class CustomizedOAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(CustomizedOAuth2LoginSuccessHandler.class);

	@Value("${APP_URL:http://localhost:5173}")
	private String appUrl;

	public CustomizedOAuth2LoginSuccessHandler(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
		Map<String, Object> attributes = Objects.requireNonNull(authenticationToken.getPrincipal()).getAttributes();
		UserDTO oAuthUser;
		try {
			oAuthUser = userService.createOAuthUser(attributes);
			if (oAuthUser != null) {
				userService.createUser(oAuthUser);
			}
		} catch (InvalidAuthenticationException e) {
			logger.error("Could not create user: ", e);
		} finally {
			response.sendRedirect(appUrl + "/dashboard");
		}
	}
}
