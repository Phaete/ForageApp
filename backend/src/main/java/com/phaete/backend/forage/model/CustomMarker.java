package com.phaete.backend.forage.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Representation of a custom marker in the database.
 * A custom marker is a point on the map with a given icon and pop-up text.
 *
 * @param id a unique identifier for the marker in the database
 * @param position an array of length 2: [latitude, longitude]
 * @param icon a custom object as described in the {@link Icon} class
 * @param popupText a string that will be displayed in a pop-up when the
 * marker is clicked
 *
 * @author -St4n aka Phaete
 */
public record CustomMarker(
		String id,
		double[] position,
		Icon icon,
		String popupText
) {
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CustomMarker that = (CustomMarker) o;
		return Objects.equals(id, that.id) && Objects.equals(icon, that.icon) && Objects.equals(popupText, that.popupText) && Objects.deepEquals(position, that.position);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, Arrays.hashCode(position), icon, popupText);
	}

	@Override
	public String toString() {
		return "CustomMarker{" +
				"id='" + id + '\'' +
				", position=" + Arrays.toString(position) +
				", icon=" + icon +
				", popupText='" + popupText + '\'' +
				'}';
	}
}
