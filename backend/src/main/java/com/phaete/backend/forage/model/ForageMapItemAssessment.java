package com.phaete.backend.forage.model;

/**
 * Represents an assessment of a forage map item.
 * <p>
 * This assessment consists of two attributes:
 * <ul>
 *     <li>{@link ForageQuality} - the quality of the forage item at the location of the forage map item.</li>
 *     <li>{@link ForageQuantity} - the quantity of the forage item at the location of the forage map item.</li>
 * </ul>
 *
 * @author -St4n aka Phaete
 */
public record ForageMapItemAssessment(
		ForageQuality quality,
		ForageQuantity quantity
) {
}
