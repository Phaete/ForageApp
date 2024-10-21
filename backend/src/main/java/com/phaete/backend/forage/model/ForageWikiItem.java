package com.phaete.backend.forage.model;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Objects;


public class ForageWikiItem {
	@Id private String id;
	private String name;
	private ForageCategory category;
	private ForageSource source;
	private String description;
	private ForageSeason season;
	private List<String> imageURLs;

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
	public ForageWikiItem(
			String id,
			String name,
			ForageCategory category,
			ForageSource source,
			String description,
			ForageSeason season,
			List<String> imageURLs
	) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.source = source;
		this.description = description;
		this.season = season;
		this.imageURLs = imageURLs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ForageCategory getCategory() {
		return category;
	}

	public void setCategory(ForageCategory category) {
		this.category = category;
	}

	public ForageSource getSource() {
		return source;
	}

	public void setSource(ForageSource source) {
		this.source = source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ForageSeason getSeason() {
		return season;
	}

	public void setSeason(ForageSeason season) {
		this.season = season;
	}

	public List<String> getImageURLs() {
		return imageURLs;
	}

	public void setImageURL(List<String> imageURLs) {
		this.imageURLs = imageURLs;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ForageWikiItem that = (ForageWikiItem) o;
		return Objects.equals(id, that.id) && Objects.equals(name, that.name) && category == that.category && source == that.source && Objects.equals(description, that.description) && season == that.season && Objects.equals(imageURLs, that.imageURLs);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, category, source, description, season, imageURLs);
	}

	@Override
	public String toString() {
		return "ForageWikiItem{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", category=" + category +
				", source=" + source +
				", description='" + description + '\'' +
				", season=" + season +
				", imageURLs=" + imageURLs +
				'}';
	}
}
