package com.phaete.backend.forage.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Arrays;
import java.util.Objects;

@Document(collection = "forageMapItem")
public record ForageMapItem(
			@Id String id,
			@DocumentReference ForageWikiItem forageWikiItem,
			@DocumentReference CustomMarker customMarker,
			double[] position,
			ForageQuantity quantity,
			ForageQuality quality,
			String notes
) {

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ForageMapItem that = (ForageMapItem) o;
		return Objects.equals(id, that.id) && Objects.equals(forageWikiItem, that.forageWikiItem) && Objects.equals(customMarker, that.customMarker) && Objects.deepEquals(position, that.position) && quantity == that.quantity && quality == that.quality && Objects.equals(notes, that.notes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, forageWikiItem, customMarker, Arrays.hashCode(position), quantity, quality, notes);
	}

	@Override
	public String toString() {
		return "ForageMapItem{" +
				"id='" + id + '\'' +
				", forageWikiItem=" + forageWikiItem +
				", customMarker=" + customMarker +
				", position=" + Arrays.toString(position) +
				", quantity=" + quantity +
				", quality=" + quality +
				", notes='" + notes + '\'' +
				'}';
	}
}



