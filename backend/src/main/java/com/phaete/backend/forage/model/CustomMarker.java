package com.phaete.backend.forage.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents a custom marker in the database.
 * A custom marker is a point on the map with a given icon and pop-up text.
 * <ul>
 * <li> The id is a unique identifier for the marker in the database. </li>
 * <li> The position is an array of length 2: [latitude, longitude] </li>
 * <li> The icon is a custom object as described in the IconModel class. </li>
 * <li> The pop-up text is a string that will be displayed in a pop-up when the
 * marker is clicked. </li>
 * </ul>
 */
public record CustomMarker(
		String id,
		double[] position,
		Icon icon,
		String popUpText
) {
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CustomMarker that = (CustomMarker) o;
		return Objects.equals(id, that.id) && Objects.equals(icon, that.icon) && Objects.equals(popUpText, that.popUpText) && Objects.deepEquals(position, that.position);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, Arrays.hashCode(position), icon, popUpText);
	}

	@Override
	public String toString() {
		return "CustomMarker{" +
				"id='" + id + '\'' +
				", position=" + Arrays.toString(position) +
				", icon=" + icon +
				", popUpText='" + popUpText + '\'' +
				'}';
	}
}
