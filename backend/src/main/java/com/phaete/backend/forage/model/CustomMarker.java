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
public class CustomMarker {
	@Id private	String id;
	private double[] position;
	private String iconUrl;
	private int[] iconSize;
	private int[] iconAnchor;
	private int[] popupAnchor;
	private String popupText;


	public CustomMarker(
			String id,
			double[] position,
			String iconUrl,
			int[] iconSize,
			int[] iconAnchor,
			int[] popupAnchor,
			String popupText
	) {
		this.id = id;
		this.position = position;
		this.iconUrl = iconUrl;
		this.iconSize = iconSize;
		this.iconAnchor = iconAnchor;
		this.popupAnchor = popupAnchor;
		this.popupText = popupText;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] position) {
		this.position = position;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public int[] getIconSize() {
		return iconSize;
	}

	public void setIconSize(int[] iconSize) {
		this.iconSize = iconSize;
	}

	public int[] getIconAnchor() {
		return iconAnchor;
	}

	public void setIconAnchor(int[] iconAnchor) {
		this.iconAnchor = iconAnchor;
	}

	public int[] getPopupAnchor() {
		return popupAnchor;
	}

	public void setPopupAnchor(int[] popupAnchor) {
		this.popupAnchor = popupAnchor;
	}

	public String getPopupText() {
		return popupText;
	}

	public void setPopupText(String popupText) {
		this.popupText = popupText;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CustomMarker that = (CustomMarker) o;
		return Objects.equals(id, that.id) && Objects.equals(iconUrl, that.iconUrl) && Objects.deepEquals(iconSize, that.iconSize) && Objects.deepEquals(iconAnchor, that.iconAnchor) && Objects.equals(popupText, that.popupText) && Objects.deepEquals(position, that.position) && Objects.deepEquals(popupAnchor, that.popupAnchor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, Arrays.hashCode(position), iconUrl, Arrays.hashCode(iconSize), Arrays.hashCode(iconAnchor), Arrays.hashCode(popupAnchor), popupText);
	}

	@Override
	public String toString() {
		return "CustomMarker{" +
				"id='" + id + '\'' +
				", position=" + Arrays.toString(position) +
				", iconUrl='" + iconUrl + '\'' +
				", iconSize=" + Arrays.toString(iconSize) +
				", iconAnchor=" + Arrays.toString(iconAnchor) +
				", popupAnchor=" + Arrays.toString(popupAnchor) +
				", popupText='" + popupText + '\'' +
				'}';
	}
}
