package com.phaete.backend.forage.model;

/**
 * Exception thrown when a {@link ForageWikiItem} can't be found in the database.
 */
public class ForageWikiItemNotFoundException extends RuntimeException {

	public ForageWikiItemNotFoundException(String message) {
		super(message);
	}
}
