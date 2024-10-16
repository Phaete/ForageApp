package com.phaete.backend.forage.model;

import java.util.Arrays;
import java.util.Objects;

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
 * @author -St4n aka Phaete
 */
public record ForageWikiItem(
		String id,
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
		ForageWikiItem that = (ForageWikiItem) o;
		return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(season, that.season) && Objects.equals(description, that.description) && Objects.deepEquals(imageURLs, that.imageURLs) && source == that.source && category == that.category;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, category, source, description, season, Arrays.hashCode(imageURLs));
	}

	@Override
	public String toString() {
		return "ForageWikiItem{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", category=" + category +
				", source=" + source +
				", description='" + description + '\'' +
				", season='" + season + '\'' +
				", imageURLs=" + Arrays.toString(imageURLs) +
				'}';
	}
}
