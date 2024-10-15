package com.phaete.backend.forage.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * A model for the icon information of a custom marker.
 *
 * @author -St4n aka Phaete
 */
public record Icon(
		String iconUrl,
		int[] iconSize,
		int[] iconAnchor,
		int[] popupAnchor
) {
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Icon icon = (Icon) o;
		return Objects.equals(iconUrl, icon.iconUrl) && Objects.deepEquals(iconSize, icon.iconSize) && Objects.deepEquals(iconAnchor, icon.iconAnchor) && Objects.deepEquals(popupAnchor, icon.popupAnchor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(iconUrl, Arrays.hashCode(iconSize), Arrays.hashCode(iconAnchor), Arrays.hashCode(popupAnchor));
	}

	@Override
	public String toString() {
		return "IconModel{" +
				"iconUrl='" + iconUrl + '\'' +
				", iconSize=" + Arrays.toString(iconSize) +
				", iconAnchor=" + Arrays.toString(iconAnchor) +
				", popupAnchor=" + Arrays.toString(popupAnchor) +
				'}';
	}
}
