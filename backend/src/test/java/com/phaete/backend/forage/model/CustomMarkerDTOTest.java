package com.phaete.backend.forage.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CustomMarkerDTOTest {

	@Test
	void testEquals() {
		CustomMarkerDTO customMarker1DTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				null,
				null
		);
		CustomMarkerDTO customMarker2DTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				null,
				null
		);

		assertTrue(customMarker1DTO.equals(customMarker2DTO));
	}

	@Test
	void testEquals_withDifferentPosition() {
		CustomMarkerDTO customMarker1DTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				null,
				null
		);
		CustomMarkerDTO customMarker2DTO = new CustomMarkerDTO(
				new double[] {0.0, 1.0},
				null,
				null
		);

		assertFalse(customMarker1DTO.equals(customMarker2DTO));
	}

	@Test
	void testEquals_null() {
		CustomMarkerDTO customMarker1DTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				null,
				null
		);
		CustomMarkerDTO customMarker2DTO = null;

		assertFalse(customMarker1DTO.equals(customMarker2DTO));
	}

	@Test
	void testHashCode() {
		CustomMarkerDTO customMarker1DTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				null,
				null
		);
		CustomMarkerDTO customMarker2DTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				null,
				null
		);

		assertEquals(customMarker1DTO.hashCode(), customMarker2DTO.hashCode());
	}

	@Test
	void testToString() {
		CustomMarkerDTO customMarker = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				null,
				null
		);

		String expected = "CustomMarkerDTO{" +
				"position=" + Arrays.toString(customMarker.position()) +
				", icon=" + customMarker.icon() +
				", popupText='" + customMarker.popupText() + '\'' +
				'}';

		assertEquals(expected, customMarker.toString());
	}
}