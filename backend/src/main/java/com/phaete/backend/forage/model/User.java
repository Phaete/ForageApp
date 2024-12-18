package com.phaete.backend.forage.model;

/**
 * Representation of a forage app user in the database.
 * A User is an object representing the details as well as authorities of a forage app user.
 * <p>
 * @param id the id of the user
 * @param origin the origin of the user, contains the provider, e.g. github or google as well as the unique
 *               user_id from that provider
 * @param name the name of the user
 * @param email the email address of the user
 * @param imageUrl the url to the image of the user
 * @param role the users authority
 *
 * @author -St4n aka Phaete
 */
public record User(
		String id,
		String origin,
		String name,
		String email,
		String imageUrl,
		Role role
) {

}
