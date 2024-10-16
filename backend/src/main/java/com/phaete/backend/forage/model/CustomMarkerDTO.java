package com.phaete.backend.forage.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * A Data Transfer Object for the {@link CustomMarker} entity.
 * It contains only the fields that are relevant for the frontend.
 *
 * @param position an array of length 2: [latitude, longitude]
 * @param icon the icon of the custom marker
 * @param popupText the text that will be displayed in a pop-up when the marker is clicked
 *
 * @author -St4n aka Phaete
 */
public record CustomMarkerDTO(
		double[] position,
		Icon icon,
		String popupText
) {
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CustomMarkerDTO that = (CustomMarkerDTO) o;
		return Objects.equals(icon, that.icon) && Objects.equals(popupText, that.popupText) && Objects.deepEquals(position, that.position);
	}

	@Override
	public int hashCode() {
		return Objects.hash(Arrays.hashCode(position), icon, popupText);
	}

	@Override
	public String toString() {
		return "CustomMarkerDTO{" +
				"position=" + Arrays.toString(position) +
				", icon=" + icon +
				", popupText='" + popupText + '\'' +
				'}';
	}
}
