package com.phaete.backend.forage.model;

/**
 * A Data Transfer Object for the {@link ForageMapItem} entity.
 * It contains only the fields that are relevant for the frontend.
 *
 * @param forageWikiItem the forage item associated with the map item
 * @param customMarker the custom marker associated with the map item
 * @param position the geographical position of the map item
 * @param ownership the ownership of the forage item on the map
 * @param assessment the assessment of the forage item on the map
 * @param notes additional notes about the forage map item
 *
 * @author -St4n aka Phaete
 */
public record ForageMapItemDTO(
		ForageWikiItem forageWikiItem,
		CustomMarker customMarker,
		GeoPosition position,
		ForageMapItemOwnership ownership,
		ForageMapItemAssessment assessment,
		String notes
) {

}
