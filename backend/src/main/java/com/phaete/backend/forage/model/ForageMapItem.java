package com.phaete.backend.forage.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * Representation of a forage map item in the database.
 * A ForageMapItem is an object representing the relation between a forage item and a marker on the map.
 * <p>
 * @param id the id of the forage map item
 * @param forageWikiItem the forage item associated with the map item
 * @param customMarker the custom marker associated with the map item
 * @param position the geographical position of the map item
 * @param assessment the assessment of the forage item on the map
 * @param notes additional notes about the forage map item
 *
 * @author -St4n aka Phaete
 */
@Document(collection = "forageMapItem")
public record ForageMapItem(
		@Id String id,
		@DocumentReference ForageWikiItem forageWikiItem,
		@DocumentReference CustomMarker customMarker,
		GeoPosition position,
		ForageMapItemAssessment assessment,
		String notes
) {

}



