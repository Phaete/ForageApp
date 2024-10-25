package com.phaete.backend.forage.model;

import java.util.Arrays;
import java.util.Objects;

public record ForageMapItemDTO(
		ForageWikiItem forageWikiItem,
		CustomMarker customMarker,
		double[] position,
		ForageQuantity quantity,
		ForageQuality quality,
		String notes
) {
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ForageMapItemDTO that = (ForageMapItemDTO) o;
		return Objects.equals(notes, that.notes) && Objects.deepEquals(position, that.position) && quality == that.quality && quantity == that.quantity && Objects.equals(customMarker, that.customMarker) && Objects.equals(forageWikiItem, that.forageWikiItem);
	}

	@Override
	public int hashCode() {
		return Objects.hash(forageWikiItem, customMarker, Arrays.hashCode(position), quantity, quality, notes);
	}

	@Override
	public String toString() {
		return "ForageMapItemDTO{" +
				"forageWikiItem=" + forageWikiItem +
				", customMarker=" + customMarker +
				", position=" + Arrays.toString(position) +
				", quantity=" + quantity +
				", quality=" + quality +
				", notes='" + notes + '\'' +
				'}';
	}
}
