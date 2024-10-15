package com.phaete.backend.forage.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CustomMarkerTest {

	@Test
	void testEquals() {
		CustomMarker customMarker1 = new CustomMarker(
				"1",
				new double[] {0.0, 0.0},
				null,
				null
		);
		 CustomMarker customMarker2 = new CustomMarker(
				 "1",
				 new double[] {0.0, 0.0},
				 null,
				 null
		 );

		 assertTrue(customMarker1.equals(customMarker2));
	}

	@Test
	void testEquals_withDifferentId() {
		CustomMarker customMarker1 = new CustomMarker(
				"1",
				new double[] {0.0, 0.0},
				null,
				null
		);
		CustomMarker customMarker2 = new CustomMarker(
				"2",
				new double[] {0.0, 0.0},
				null,
				null
		);

		assertFalse(customMarker1.equals(customMarker2));
	}

	@Test
	void testEquals_null() {
		CustomMarker customMarker1 = new CustomMarker(
				"1",
				new double[] {0.0, 0.0},
				null,
				null
		);
		CustomMarker customMarker2 = null;

		assertFalse(customMarker1.equals(customMarker2));
	}

	@Test
	void testHashCode() {
		CustomMarker customMarker1 = new CustomMarker(
				"1",
				new double[] {0.0, 0.0},
				null,
				null
		);
		CustomMarker customMarker2 = new CustomMarker(
				"1",
				new double[] {0.0, 0.0},
				null,
				null
		);

		assertEquals(customMarker1.hashCode(), customMarker2.hashCode());
	}

	@Test
	void testToString() {
		CustomMarker customMarker = new CustomMarker(
				"1",
				new double[] {0.0, 0.0},
				null,
				null
		);

		String expected = "CustomMarker{" +
				"id='" + customMarker.id() + '\'' +
				", position=" + Arrays.toString(customMarker.position()) +
				", icon=" + customMarker.icon() +
				", popupText='" + customMarker.popupText() + '\'' +
				'}';

		assertEquals(expected, customMarker.toString());
	}
}