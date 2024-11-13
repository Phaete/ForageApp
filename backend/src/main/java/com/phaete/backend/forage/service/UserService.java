package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.*;
import com.phaete.backend.forage.repository.UserRepository;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service class for managing {@link User}.
 * It provides methods to interact with the {@link UserRepository}
 * allowing for CRUD operations on User objects.
 * <p>
 * Supported functions as per CRUD:
 * <ul>
 *     <li> Creating new users </li>
 *     <li> Retrieving all users </li>
 *     <li> Retrieving users by id </li>
 *     <li> Updating users by id </li>
 *     <li> Deleting users by id </li>
 * </ul>
 * <p>
 * The service acts as a bridge between the controller and the repository
 * and contains all business logic for the User objects.
 * <p>
 * @author -St4n aka Phaete
 */
@Service
public class UserService {

	private final UserRepository userRepository;
	private final ConverterService converterService;

	public UserService(UserRepository userRepository, ConverterService converterService) {
		this.userRepository = userRepository;
		this.converterService = converterService;
	}

	/**
	 * Creates a new user and saves it to the database.
	 *
	 * @param userDTO the user to the saved to be created
	 * @return the newly created user
	 */
	public UserDTO createUser(UserDTO userDTO) {
		return converterService.toDTO(
				userRepository.save(
						converterService.fromDTO(
								userDTO
						)
				)
		);
	}

	/**
	 * Retrieves all users from the database.
	 *
	 * @return a list of all users
	 */
	public List<User> findAll() {
		return userRepository.findAll();
	}

	/**
	 * Constructs the origin of a user from the attributes of an authentication token.
	 * The origin is a string made up of the provider and the id of the user.
	 * @param attributes the attributes of a users authentication token
	 * @return the origin of the user
	 * @throws InvalidAuthenticationException if the origin cannot be determined
	 */
	private String getOriginFromAttributes(Map<String, Object> attributes) throws InvalidAuthenticationException {
		if (attributes.containsKey("avatar_url")) {
			return "github:"+attributes.get("id");
		} else if (attributes.containsKey("picture")) {
			return "google:"+attributes.get("sub");
		} else {
			throw new InvalidAuthenticationException("Could not determine the origin of the user.");
		}
	}

	/**
	 * Creates a UserDTO from the attributes of an authentication token and an origin.
	 *
	 * @param attributes the attributes of a users authentication token
	 * @param origin the origin of the user
	 * @return the created UserDTO or null if the user with the specified origin already exists
	 */
	private UserDTO createUserDTOFromAttributes(Map<String, Object> attributes, String origin) {
		if (userRepository.findByOrigin(origin).isEmpty()) {
			return new UserDTO(
					origin,
					Optional.ofNullable(attributes.get("name")).map(Object::toString).orElse(null),
					Optional.ofNullable(attributes.get("email")).map(Object::toString).orElse(null),
					origin.startsWith("github") ? attributes.get("avatar_url").toString() : attributes.get("picture").toString(),
					Role.USER
			);
		}
		return null;
	}

	/**
	 * Creates a user from the attributes of an authentication token.
	 *
	 * @param attributes the attributes of a users authentication token
	 * @return the created UserDTO
	 * @throws InvalidAuthenticationException if the origin cannot be determined
	 */
	public UserDTO createOAuthUser(Map<String, Object> attributes) throws InvalidAuthenticationException {
		return createUserDTOFromAttributes(attributes, getOriginFromAttributes(attributes));
	}

	/**
	 * Retrieves a user by their origin.
	 *
	 * @param origin the origin of the user
	 * @return the user
	 * @throws UserNotFoundException if no user with the specified origin exists
	 */
	public UserDTO getUserByOrigin(String origin) throws UserNotFoundException {
		return converterService.toDTO(
				userRepository.findByOrigin(origin).orElseThrow(
						() -> new UserNotFoundException("Could not find a user with the specified origin.")
				)
		);
	}

	/**
	 * Retrieves a user by the attributes of an authentication token.
	 *
	 * @param attributes the attributes of a users authentication token
	 * @return the user
	 * @throws InvalidAuthenticationException if the origin of the authentication token cannot be determined
	 * @throws UserNotFoundException if no user with the attributes of the authentication token exists
	 */
	public UserDTO getUserByAttributes(Map<String, Object> attributes) throws InvalidAuthenticationException, UserNotFoundException {
		return getUserByOrigin(getOriginFromAttributes(attributes));
	}

	/**
	 * Updates an existing user in the database.
	 *
	 * @param userDTO the user to be updated
	 * @param authentication a users authentication token
	 * @return the updated user
	 * @throws UserNotFoundException if no user belonging to the authentication token exists
	 * @throws InvalidAuthenticationException if the current user is not authenticated to update the specified user
	 */
	public UserDTO updateUser(UserDTO userDTO, OAuth2AuthenticationToken authentication) throws UserNotFoundException, InvalidAuthenticationException {
		UserDTO currentUser = getUserByAttributes(authentication.getPrincipal().getAttributes());

		if (currentUser.origin().equals(userDTO.origin()) || userDTO.role().equals(Role.ADMIN)) {
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
