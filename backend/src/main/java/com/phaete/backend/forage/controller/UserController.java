package com.phaete.backend.forage.controller;

import com.phaete.backend.forage.model.*;
import com.phaete.backend.forage.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public UserDTO createUser(@RequestBody UserDTO userDTO) {
		return userService.createUser(userDTO);
	}

	@GetMapping
	public List<User> findAll() {
		return userService.findAll();
	}

	@GetMapping("/me")
	public UserDTO findMe(OAuth2AuthenticationToken authentication)
			throws UserNotFoundException, InvalidAuthenticationException {
		return userService.getUserByAttributes(authentication.getPrincipal().getAttributes());
	}

	@PutMapping
	public UserDTO updateUser(@RequestBody UserDTO userDTO, OAuth2AuthenticationToken authentication)
			throws UserNotFoundException, InvalidAuthenticationException {
		return userService.updateUser(userDTO,authentication);
	}

	@ExceptionHandler(InvalidAuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<String> handleInvalidAuthenticationException(InvalidAuthenticationException e) {
		return new ResponseEntity<>(String.format("%s: %s with Stacktrace:%n %s",
				e.getClass().getSimpleName(),
				e.getMessage(),
				Arrays.toString(e.getStackTrace())), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
		return new ResponseEntity<>(String.format("%s: %s with Stacktrace:%n %s",
				e.getClass().getSimpleName(),
				e.getMessage(),
				Arrays.toString(e.getStackTrace())), HttpStatus.NOT_FOUND);
	}
}
