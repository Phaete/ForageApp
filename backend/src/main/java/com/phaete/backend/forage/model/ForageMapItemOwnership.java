package com.phaete.backend.forage.model;

/**
 * Representation of the ownership of a forage map item.
 * <p>
 * @param owner the owner of the forage map item indicated by its unique origin string
 * @param isPublic true if the forage map item is public, false if it is private
 *
 * @author -St4n aka Phaete
 */
public record ForageMapItemOwnership(
		String owner,
		boolean isPublic
) {

}
