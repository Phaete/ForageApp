package com.phaete.backend.forage.model;

public record ForageMapItemDTO(
		ForageWikiItem forageWikiItem,
		CustomMarker customMarker,
		ForageQuantity quantity,
		ForageQuality quality,
		String dateFound,
		String notes
) {

}
