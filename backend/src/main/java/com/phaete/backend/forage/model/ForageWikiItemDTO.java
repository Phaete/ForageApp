package com.phaete.backend.forage.model;

import java.util.List;

/**
 * A Data Transfer Object for the {@link ForageWikiItem} entity.
 * It contains only the fields that are relevant for the frontend.
 *
 * @param name        the name of the forage item
 * @param category    the category of the forage item
 * @param source      the source of the forage item
 * @param description the description of the forage item
 * @param season      the harvestable season of the forage item
 * @param imageURLs   the image URLs of the forage item
 *
 * @author -St4n aka Phaete
 */
public record ForageWikiItemDTO(
		String name,
		ForageCategory category,
		ForageSource source,
		String description,
		String season,
		List<String> imageURLs
) {

}
