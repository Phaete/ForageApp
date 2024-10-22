package com.phaete.backend.forage.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Objects;


public class ForageMapItem {
	@Id private String id;
	@DocumentReference private ForageWikiItem forageWikiItem;
	@DocumentReference private CustomMarker customMarker;
	private ForageQuantity quantity;
	private ForageQuality quality;
	private String dateFound;
	private String notes;

	public ForageMapItem(
			String id,
			ForageWikiItem forageWikiItem,
			CustomMarker customMarker,
			ForageQuantity quantity,
			ForageQuality quality,
			String dateFound,
			String notes
	) {
		this.id = id;
		this.forageWikiItem = forageWikiItem;
		this.customMarker = customMarker;
		this.quantity = quantity;
		this.quality = quality;
		this.dateFound = dateFound;
		this.notes = notes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ForageWikiItem getForageWikiItem() {
		return forageWikiItem;
	}

	public void setForageWikiItem(ForageWikiItem forageWikiItem) {
		this.forageWikiItem = forageWikiItem;
	}

	public CustomMarker getCustomMarker() {
		return customMarker;
	}

	public void setCustomMarker(CustomMarker customMarker) {
		this.customMarker = customMarker;
	}

	public ForageQuantity getQuantity() {
		return quantity;
	}

	public void setQuantity(ForageQuantity quantity) {
		this.quantity = quantity;
	}

	public ForageQuality getQuality() {
		return quality;
	}

	public void setQuality(ForageQuality quality) {
		this.quality = quality;
	}

	public String getDateFound() {
		return dateFound;
	}

	public void setDateFound(String dateFound) {
		this.dateFound = dateFound;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ForageMapItem that = (ForageMapItem) o;
		return Objects.equals(id, that.id) && Objects.equals(forageWikiItem, that.forageWikiItem) && Objects.equals(customMarker, that.customMarker) && quantity == that.quantity && quality == that.quality && Objects.equals(dateFound, that.dateFound) && Objects.equals(notes, that.notes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, forageWikiItem, customMarker, quantity, quality, dateFound, notes);
	}

	@Override
	public String toString() {
		return "ForageMapItem{" +
				"id='" + id + '\'' +
				", forageWikiItem=" + forageWikiItem +
				", customMarker=" + customMarker +
				", quantity=" + quantity +
				", quality=" + quality +
				", dateFound='" + dateFound + '\'' +
				", notes='" + notes + '\'' +
				'}';
	}
}
