package com.phaete.backend.forage.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * A Data Transfer Object for the {@link CustomMarker} entity.
 * It contains only the fields that are relevant for the frontend.
 *
 * @author -St4n aka Phaete
 */
public record CustomMarkerDTO(
		String name,
		String iconUrl,
		int[] iconSize,
		int[] iconAnchor,
		int[] popupAnchor,
		String popupText
) {

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CustomMarkerDTO that = (CustomMarkerDTO) o;
		return Objects.equals(name, that.name) && Objects.equals(iconUrl, that.iconUrl) && Objects.deepEquals(iconSize, that.iconSize) && Objects.deepEquals(iconAnchor, that.iconAnchor) && Objects.equals(popupText, that.popupText) && Objects.deepEquals(popupAnchor, that.popupAnchor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, iconUrl, Arrays.hashCode(iconSize), Arrays.hashCode(iconAnchor), Arrays.hashCode(popupAnchor), popupText);
	}

	@Override
	public String toString() {
		return "CustomMarkerDTO{" +
				"name='" + name + '\'' +
				", iconUrl='" + iconUrl + '\'' +
				", iconSize=" + Arrays.toString(iconSize) +
				", iconAnchor=" + Arrays.toString(iconAnchor) +
				", popupAnchor=" + Arrays.toString(popupAnchor) +
				", popupText='" + popupText + '\'' +
				'}';
	}
}
