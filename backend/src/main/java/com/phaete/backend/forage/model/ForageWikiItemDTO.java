package com.phaete.backend.forage.model;

import java.util.Arrays;
import java.util.Objects;

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
 * @author -St4n aka Phaete
 */
public record ForageWikiItemDTO(
		String name,
		ForageCategory category,
		ForageSource source,
		String description,
		String season,
		String[] imageURLs
) {
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ForageWikiItemDTO that = (ForageWikiItemDTO) o;
		return Objects.equals(name, that.name) && Objects.equals(season, that.season) && Objects.equals(description, that.description) && Objects.deepEquals(imageURLs, that.imageURLs) && source == that.source && category == that.category;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, category, source, description, season, Arrays.hashCode(imageURLs));
	}

	@Override
	public String toString() {
		return "ForageWikiItemDTO{" +
				"name='" + name + '\'' +
				", category=" + category +
				", source=" + source +
				", description='" + description + '\'' +
				", season='" + season + '\'' +
				", imageURLs=" + Arrays.toString(imageURLs) +
				'}';
	}
}
