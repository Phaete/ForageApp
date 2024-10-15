package com.phaete.backend.forage.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class IconTest {

	@Test
	void testEquals() {
		Icon icon1 = new Icon(
				"https://example.com/icon.png",
				new int[] {32, 32},
				new int[] {16, 16},
				new int[] {0, -16}
		);

		Icon icon2 = new Icon(
				"https://example.com/icon.png",
				new int[] {32, 32},
				new int[] {16, 16},
				new int[] {0, -16}
		);

		assertTrue(icon1.equals(icon2));
	}

	@Test
	void testHashCode() {
		Icon icon1 = new Icon(
				"https://example.com/icon.png",
				new int[] {32, 32},
				new int[] {16, 16},
				new int[] {0, -16}
		);

		Icon icon2 = new Icon(
				"https://example.com/icon.png",
				new int[] {32, 32},
				new int[] {16, 16},
				new int[] {0, -16}
		);

		assertEquals(icon1.hashCode(), icon2.hashCode());
	}

	@Test
	void testToString() {
		Icon icon = new Icon(
				"https://example.com/icon.png",
				new int[] {32, 32},
				new int[] {16, 16},
				new int[] {0, -16}
		);

		String expected = "IconModel{" +
				"iconUrl='" + icon.iconUrl() + '\'' +
				", iconSize=" + Arrays.toString(icon.iconSize()) +
				", iconAnchor=" + Arrays.toString(icon.iconAnchor()) +
				", popupAnchor=" + Arrays.toString(icon.popupAnchor()) +
				'}';

		assertEquals(expected, icon.toString());
	}
}