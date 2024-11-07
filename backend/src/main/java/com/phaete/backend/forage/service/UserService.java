package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.*;
import com.phaete.backend.forage.repository.UserRepository;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final ConverterService converterService;

	public UserService(UserRepository userRepository, ConverterService converterService) {
		this.userRepository = userRepository;
		this.converterService = converterService;
	}

	public UserDTO createUser(UserDTO userDTO) {
		return converterService.toDTO(
				userRepository.save(
						converterService.fromDTO(
								userDTO
						)
				)
		);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	private String getOriginFromAttributes(Map<String, Object> attributes) throws InvalidAuthenticationException {
		if (attributes.containsKey("avatar_url")) {
			return "github:"+attributes.get("id");
		} else if (attributes.containsKey("picture")) {
			return "google:"+attributes.get("sub");
		} else {
			throw new InvalidAuthenticationException("Could not determine the origin of the user.");
		}
	}

	private UserDTO createUserDTOFromAttributes(Map<String, Object> attributes, String origin) {
		if (userRepository.findByOrigin(origin).isEmpty()) {
			return new UserDTO(
					origin,
					attributes.get("name").toString(),
					attributes.get("email").toString(),
					null,
					origin.startsWith("github") ? attributes.get("avatar_url").toString() : attributes.get("picture").toString(),
					Role.USER
			);
		}
		return null;
	}

	public UserDTO createOAuthUser(Map<String, Object> attributes) throws InvalidAuthenticationException {
		return createUserDTOFromAttributes(attributes, getOriginFromAttributes(attributes));
	}

	public UserDTO getUserByOrigin(String origin) throws UserNotFoundException {
		return converterService.toDTO(
				userRepository.findByOrigin(origin).orElseThrow(
						() -> new UserNotFoundException("Could not find a user with the specified origin.")
				)
		);
	}

	public UserDTO getUserByAttributes(Map<String, Object> attributes) throws InvalidAuthenticationException, UserNotFoundException {
		return getUserByOrigin(getOriginFromAttributes(attributes));
	}

	public UserDTO updateUser(UserDTO userDTO, OAuth2AuthenticationToken authentication) throws UserNotFoundException, InvalidAuthenticationException {
		UserDTO currentUser = getUserByAttributes(authentication.getPrincipal().getAttributes());

		if (currentUser.origin().equals(userDTO.origin()) || currentUser.role().equals(Role.ADMIN)) {
			return converterService.toDTO(
					userRepository.save(
							converterService.fromDTO(
									userDTO
							)
					)
			);
		} else {
			throw new InvalidAuthenticationException("You are not authenticated to update this user.");
		}
	}
}
