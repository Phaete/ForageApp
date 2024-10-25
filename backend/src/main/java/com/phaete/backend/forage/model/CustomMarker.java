package com.phaete.backend.forage.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Objects;

/**
 * A model for a custom marker.
 * <p>
 * It contains the following information about a custom marker:
 * <ul>
 * 	<li> id - the id of the custom marker </li>
 * 	<li> position - the position of the custom marker as a double array with length 2 </li>
 * 	<li> iconUrl - the URL of the icon image </li>
 * 	<li> iconSize - the size of the icon image </li>
 * 	<li> iconAnchor - the anchor position of the icon image </li>
 * 	<li> popupAnchor - the anchor position of the popup text </li>
 * 	<li> popupText - the text to be displayed in the popup </li>
 * </ul>
 *
 * @author -St4n aka Phaete
 */
@Document()
public record CustomMarker(
		@Id String id,
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
		CustomMarker that = (CustomMarker) o;
		return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(iconUrl, that.iconUrl) && Objects.deepEquals(iconSize, that.iconSize) && Objects.deepEquals(iconAnchor, that.iconAnchor) && Objects.equals(popupText, that.popupText) && Objects.deepEquals(popupAnchor, that.popupAnchor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, iconUrl, Arrays.hashCode(iconSize), Arrays.hashCode(iconAnchor), Arrays.hashCode(popupAnchor), popupText);
	}

	@Override
	public String toString() {
		return "CustomMarker{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", iconUrl='" + iconUrl + '\'' +
				", iconSize=" + Arrays.toString(iconSize) +
				", iconAnchor=" + Arrays.toString(iconAnchor) +
				", popupAnchor=" + Arrays.toString(popupAnchor) +
				", popupText='" + popupText + '\'' +
				'}';
	}
}
