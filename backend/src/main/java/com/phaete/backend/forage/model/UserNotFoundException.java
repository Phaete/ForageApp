package com.phaete.backend.forage.model;

public class UserNotFoundException extends Exception {
	public UserNotFoundException(String message) {
		super(message);
	}
}
