package com.phaete.backend.forage.model;

/**
 * Exception thrown when a marker can't be found in the database.
 */
public class MarkerNotFoundException extends RuntimeException {

	public MarkerNotFoundException(String message) {
		super(message);
	}
}
