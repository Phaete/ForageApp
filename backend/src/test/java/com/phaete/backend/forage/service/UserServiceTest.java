package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.*;
import com.phaete.backend.forage.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

	private final UserRepository userRepository = mock(UserRepository.class);
	private final IdService idService = mock(IdService.class);
	private final OAuth2AuthenticationToken oAuth2AuthenticationToken = mock(OAuth2AuthenticationToken.class);

	UserService userService = new UserService(userRepository, new ConverterService(idService));

	@Test
	void createUser_expectDTO_onSuccess() {
		UserDTO expectedUserDTO = new UserDTO(
				"origin",
				"name",
				"email",
				"password",
				"imageUrl",
				Role.USER
		);
		when(idService.generateId()).thenReturn("1");
		when(userRepository.save(any(User.class))).thenReturn(
				new User(
						"1",
						"origin",
						"name",
						"email",
						"password",
						"imageUrl",
						Role.USER
				)
		);

		UserDTO actualUserDTO = userService.createUser(expectedUserDTO);
		verify(userRepository).save(any(User.class));
		assertEquals(expectedUserDTO, actualUserDTO);
	}

	@Test
	void findAll_returnsList() {
		List<User> expectedList = List.of(
				new User(
						"1",
						"origin",
						"name",
						"email",
						"password",
						"imageUrl",
						Role.USER
				)
		);
		when(userRepository.findAll()).thenReturn(List.of(
				new User(
						"1",
						"origin",
						"name",
						"email",
						"password",
						"imageUrl",
						Role.USER
				)
		));

		List<User> actualList = userService.findAll();
		verify(userRepository).findAll();
		assertEquals(expectedList, actualList);
	}

	@Test
	void createOAuthUser_returnsGithubUserDTO_ifNotInDB() throws InvalidAuthenticationException {
		UserDTO expectedUserDTO = new UserDTO(
				"github:1234",
				"name",
				"email",
				null,
				"imageUrl",
				Role.USER
		);
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.empty());

		UserDTO actualUserDTO = userService.createOAuthUser(
				Map.ofEntries(
					Map.entry("id", "1234"),
					Map.entry("name", "name"),
					Map.entry("email", "email"),
					Map.entry("avatar_url", "imageUrl")
				));
		verify(userRepository).findByOrigin(any(String.class));
		assertEquals(expectedUserDTO, actualUserDTO);
	}

	@Test
	void createOAuthUser_returnsGoogleUserDTO_ifNotInDB() throws InvalidAuthenticationException {
		UserDTO expectedUserDTO = new UserDTO(
				"google:1234",
				"name",
				"email",
				null,
				"imageUrl",
				Role.USER
		);
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.empty());

		UserDTO actualUserDTO = userService.createOAuthUser(
				Map.ofEntries(
						Map.entry("sub", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("picture", "imageUrl")
				));
		verify(userRepository).findByOrigin(any(String.class));
		assertEquals(expectedUserDTO, actualUserDTO);
	}

	@Test
	void createOAuthUser_returnsNull_ifGithubUserAlreadyInDB() throws InvalidAuthenticationException {
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"name",
						"email",
						"password",
						"imageUrl",
						Role.USER
				)
		));

		UserDTO actualUserDTO = userService.createOAuthUser(
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				));
		verify(userRepository).findByOrigin(any(String.class));
		assertNull(actualUserDTO);
	}

	@Test
	void createOAuthUser_returnsNull_ifGoogleUserAlreadyInDB() throws InvalidAuthenticationException {
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"google:1234",
						"name",
						"email",
						"password",
						"imageUrl",
						Role.USER
				)
		));

		UserDTO actualUserDTO = userService.createOAuthUser(
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("picture", "imageUrl")
				));
		verify(userRepository).findByOrigin(any(String.class));
		assertNull(actualUserDTO);
	}

	@Test
	void createOAuthUser_throwsInvalidAuthenticationException() {
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.empty());

		assertThrows(InvalidAuthenticationException.class, () -> userService.createOAuthUser(
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("image", "imageUrl")
				)));
	}

	@Test
	void getUserByOrigin_returnsUserDTO() throws UserNotFoundException {
		UserDTO expectedUserDTO = new UserDTO(
				"forage-app:1234",
				"name",
				"email",
				"password",
				"imageUrl",
				Role.USER
		);
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
						new User(
								"1",
								"forage-app:1234",
								"name",
								"email",
								"password",
								"imageUrl",
								Role.USER
						)
		));

		UserDTO actualUserDTO = userService.getUserByOrigin("forage-app:1234");
		verify(userRepository).findByOrigin(any(String.class));
		assertEquals(expectedUserDTO, actualUserDTO);
	}

	@Test
	void getUserByOrigin_throwsUserNotFoundException() {
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.getUserByOrigin("forage-app:1234"));
	}

	@Test
	void getUserByAttributes_returnsGithubUserDTO() throws InvalidAuthenticationException, UserNotFoundException {
		UserDTO expectedUserDTO = new UserDTO(
				"github:1234",
				"name",
				"email",
				null,
				"imageUrl",
				Role.USER
		);
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"name",
						"email",
						null,
						"imageUrl",
						Role.USER
				)
		));

		UserDTO actualUserDTO = userService.getUserByAttributes(
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				));
		verify(userRepository).findByOrigin(any(String.class));
		assertEquals(expectedUserDTO, actualUserDTO);
	}

	@Test
	void getUserByAttributes_returnsGoogleUserDTO() throws InvalidAuthenticationException, UserNotFoundException {
		UserDTO expectedUserDTO = new UserDTO(
				"google:1234",
				"name",
				"email",
				null,
				"imageUrl",
				Role.USER
		);
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"google:1234",
						"name",
						"email",
						null,
						"imageUrl",
						Role.USER
				)
		));

		UserDTO actualUserDTO = userService.getUserByAttributes(
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("picture", "imageUrl")
				));
		verify(userRepository).findByOrigin(any(String.class));
		assertEquals(expectedUserDTO, actualUserDTO);
	}

	@Test
	void getUserByAttributes_throwsInvalidAuthenticationException() {
		assertThrows(InvalidAuthenticationException.class, () -> userService.getUserByAttributes(
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("image", "imageUrl")
				)
		));
	}

	@Test
	void updateUser_returnsUserDTO_onUserSuccess() throws UserNotFoundException, InvalidAuthenticationException {
		UserDTO expectedUserDTO = new UserDTO(
				"github:1234",
				"name123",
				"email",
				null,
				"imageUrl",
				Role.USER
		);
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"name",
						"email",
						null,
						"imageUrl",
						Role.USER
				)
		));
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
					Map.entry("id", "1234"),
					Map.entry("name", "name"),
					Map.entry("email", "email"),
					Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.save(any(User.class))).thenReturn(
				new User(
						"1",
						"github:1234",
						"name123",
						"email",
						null,
						"imageUrl",
						Role.USER
				)
		);

		UserDTO actualUserDTO = userService.updateUser(expectedUserDTO, new OAuth2AuthenticationToken(
				new DefaultOAuth2User(
						null,
						Map.ofEntries(
								Map.entry("id", "1234"),
								Map.entry("name", "name"),
								Map.entry("email", "email"),
								Map.entry("avatar_url", "imageUrl")
						),
						"name"
				),
				null,
				"github"
				)
		);
		verify(userRepository).save(any(User.class));
		assertEquals(expectedUserDTO, actualUserDTO);
	}

	@Test
	void updateUser_returnsUserDTO_onAdminSuccess() throws UserNotFoundException, InvalidAuthenticationException {
		UserDTO expectedUserDTO = new UserDTO(
				"github:1234",
				"name123",
				"email",
				null,
				"imageUrl",
				Role.USER
		);
		when(userRepository.findByOrigin("github:1234")).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"name",
						"email",
						null,
						"imageUrl",
						Role.USER
				)
		));
		when(userRepository.findByOrigin("github:1")).thenReturn(Optional.of(
				new User(
						"2",
						"github:1",
						"name123",
						"email",
						null,
						"imageUrl",
						Role.ADMIN
				)
		));
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(
				new DefaultOAuth2User(
						null,
						Map.ofEntries(
								Map.entry("id", "1"),
								Map.entry("name", "name"),
								Map.entry("email", "email"),
								Map.entry("avatar_url", "imageUrl")
						),
						"name"
				)
		);
		when(userRepository.save(any(User.class))).thenReturn(
				new User(
						"1",
						"github:1234",
						"name123",
						"email",
						null,
						"imageUrl",
						Role.USER
				)
		);

		UserDTO actualUserDTO = userService.updateUser(expectedUserDTO, new OAuth2AuthenticationToken(
						new DefaultOAuth2User(
								null,
								Map.ofEntries(
										Map.entry("id", "1234"),
										Map.entry("name", "name"),
										Map.entry("email", "email"),
										Map.entry("avatar_url", "imageUrl")
								),
								"name"
						),
						null,
						"github"
				)
		);
		verify(userRepository).save(any(User.class));
		assertEquals(expectedUserDTO, actualUserDTO);
	}

@Test
	void updateUser_returnsAdminDTO_onAdminSuccess() throws UserNotFoundException, InvalidAuthenticationException {
		UserDTO expectedUserDTO = new UserDTO(
				"github:1",
				"name1",
				"email",
				null,
				"imageUrl",
				Role.ADMIN
		);
		when(userRepository.findByOrigin("github:1")).thenReturn(Optional.of(
				new User(
						"1",
						"github:1",
						"name123",
						"email",
						null,
						"imageUrl",
						Role.ADMIN
				)
		));
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(
				new DefaultOAuth2User(
						null,
						Map.ofEntries(
								Map.entry("id", "1"),
								Map.entry("name", "name"),
								Map.entry("email", "email"),
								Map.entry("avatar_url", "imageUrl")
						),
						"name"
				)
		);
		when(userRepository.save(any(User.class))).thenReturn(
				new User(
						"1",
						"github:1",
						"name1",
						"email",
						null,
						"imageUrl",
						Role.ADMIN
				)
		);

		UserDTO actualUserDTO = userService.updateUser(expectedUserDTO, new OAuth2AuthenticationToken(
						new DefaultOAuth2User(
								null,
								Map.ofEntries(
										Map.entry("id", "1"),
										Map.entry("name", "name"),
										Map.entry("email", "email"),
										Map.entry("avatar_url", "imageUrl")
								),
								"name"
						),
						null,
						"github"
				)
		);
		verify(userRepository).save(any(User.class));
		assertEquals(expectedUserDTO, actualUserDTO);
	}

	@Test
	void updateUser_throwsUserNotFoundException() {
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.empty());
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(
				new DefaultOAuth2User(
						null,
						Map.ofEntries(
								Map.entry("id", "1234"),
								Map.entry("name", "name"),
								Map.entry("email", "email"),
								Map.entry("avatar_url", "imageUrl")
						),
						"name"
				)
		);

		assertThrows(UserNotFoundException.class, () -> userService.updateUser(
				new UserDTO(
						"github:1234",
						"name123",
						"email",
						null,
						"imageUrl",
						Role.USER
				),
				new OAuth2AuthenticationToken(
						new DefaultOAuth2User(
								null,
								Map.ofEntries(
										Map.entry("id", "1234"),
										Map.entry("name", "name"),
										Map.entry("email", "email"),
										Map.entry("avatar_url", "imageUrl")
								),
								"name"
						),
						null,
						"github"
				)
		));
	}

	@Test
	void updateUser_throwsInvalidAuthenticationException() {
		when(userRepository.findByOrigin("github:1234")).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"name",
						"email",
						null,
						"imageUrl",
						Role.USER
				)
		));
		when(userRepository.findByOrigin("github:123")).thenReturn(Optional.of(
				new User(
						"2",
						"github:123",
						"name2",
						"email2",
						null,
						"imageUrl2",
						Role.USER
				)
		));
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "123"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));

		assertThrows(InvalidAuthenticationException.class, () -> userService.updateUser(
				new UserDTO(
						"github:1234",
						"name123",
						"email",
						null,
						"imageUrl",
						Role.USER
				),
				new OAuth2AuthenticationToken(
						new DefaultOAuth2User(null,
								Map.ofEntries(
										Map.entry("id", "123"),
										Map.entry("name", "name"),
										Map.entry("email", "email"),
										Map.entry("avatar_url", "imageUrl")
								),
								"name"
						),
						null,
						"github"
				)
		));
	}
}
