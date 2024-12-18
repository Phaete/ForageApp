package com.phaete.backend.forage.model;

/**
 * A Data Transfer Object for the {@link User} entity.
 * It contains only the fields that are relevant for the frontend.
 *
 * @param origin   the origin of the user, contains the provider, e.g. github or google as well as the unique
 *                 user_id from that provider
 * @param name     the name of the user
 * @param email    the email address of the user
 * @param imageUrl the url to the image of the user
 * @param role     the users authority
 * @author -St4n aka Phaete
 */
public record UserDTO(
		String origin,
		String name,
		String email,
		String imageUrl,
		Role role) {

}
