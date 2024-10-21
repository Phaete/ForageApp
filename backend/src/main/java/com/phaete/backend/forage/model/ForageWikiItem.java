package com.phaete.backend.forage.model;

/**
 * Representation of a forage wiki item in the database.
 * A ForageWikiItem is an object representing the static attributes of a forage item.
 *
 * @param id          the id of the forage item
 * @param name        the name of the forage item
 * @param category    the category of the forage item
 * @param source      the source of the forage item
 * @param description the description of the forage item
 * @param season      the harvestable season of the forage item
 * @param imageURLs   the image URLs of the forage item
 *
 * @author -St4n aka Phaete
 */
public record ForageWikiItem(
		String id,
		String name,
		ForageCategory category,
		ForageSource source,
		String description,
		ForageSeason season,
		java.util.List<String> imageURLs
) {

}
