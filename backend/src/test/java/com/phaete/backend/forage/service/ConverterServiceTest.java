package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConverterServiceTest {

	IdService idService = mock(IdService.class);

	ConverterService converterService = new ConverterService(idService);

	@Test
	void fromDTO_CustomMarkerDTO_toCustomMarker() {
		CustomMarker expectedCustomMarker = new CustomMarker(
				"1",
				new double[] {0.0, 0.0},
				"",
				new int[] {0, 0},
				new int[] {0, 0},
				new int[] {0, 0},
				""
		);

		when(idService.generateId()).thenReturn("1");

		CustomMarker actualCustomMarker = converterService.fromDTO(
				new CustomMarkerDTO(
						new double[] {0.0, 0.0},
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0},
						""
				)
		);
		assertEquals(expectedCustomMarker, actualCustomMarker);
	}

	@Test
	void toDTO_CustomMarker_toCustomMarkerDTO() {
		CustomMarkerDTO expectedCustomMarkerDTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				"",
				new int[] {0, 0},
				new int[] {0, 0},
				new int[] {0, 0},
				""
		);

		CustomMarkerDTO actualCustomMarkerDTO = converterService.toDTO(
				new CustomMarker(
						"1",
						new double[] {0.0, 0.0},
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0},
						""
				)
		);
		assertEquals(expectedCustomMarkerDTO, actualCustomMarkerDTO);
	}

	@Test
	void fromDTO_ForageWikiItemDTO_toForageWikiItem() {
		ForageWikiItem expectedForageWikiItem = new ForageWikiItem(
				"1",
				"Apple Tree",
				ForageCategory.FRUIT,
				ForageSource.TREE,
				"Apple Tree",
				ForageSeason.FALL,
				List.of("test")
		);
		when(idService.generateId()).thenReturn("1");

		ForageWikiItem actualForageWikiItem = converterService.fromDTO(
				new ForageWikiItemDTO(
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("test")
				)
		);
		assertEquals(expectedForageWikiItem, actualForageWikiItem);
	}

	@Test
	void toDTO_ForageWikiItem_toForageWikiItemDTO() {
		ForageWikiItemDTO expectedForageWikiItemDTO = new ForageWikiItemDTO(
				"Apple Tree",
				ForageCategory.FRUIT,
				ForageSource.TREE,
				"Apple Tree",
				ForageSeason.FALL,
				List.of("test")
		);

		ForageWikiItemDTO actualForageWikiItemDTO = converterService.toDTO(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("test")
				)
		);
		assertEquals(expectedForageWikiItemDTO, actualForageWikiItemDTO);
	}

	@Test
	void fromDTO_ForageMapItemDTO_toForageMapItem() {
		when(idService.generateId()).thenReturn("1");
		ForageMapItem expectedForageItem = new ForageMapItem(
				"1",
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("test")
				),
				new CustomMarker(
						"1",
						new double[] {0.0, 0.0},
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0},
						""
				),
				ForageQuantity.ABUNDANT,
				ForageQuality.EXCELLENT,
				"never",
				"notes"
		);

		ForageMapItem actualForageItem = converterService.fromDTO(
				new ForageMapItemDTO(
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						),
						new CustomMarker(
								"1",
								new double[] {0.0, 0.0},
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0},
								""
						),
						ForageQuantity.ABUNDANT,
						ForageQuality.EXCELLENT,
						"never",
						"notes"
				)
		);
		assertEquals(expectedForageItem, actualForageItem);
	}

	@Test
	void toDTO_ForageMapItem_toForageMapItemDTO() {
		ForageMapItemDTO expectedForageMapItemDTO = new ForageMapItemDTO(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("test")
				),
				new CustomMarker(
						"1",
						new double[] {0.0, 0.0},
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0},
						""
				),
				ForageQuantity.ABUNDANT,
				ForageQuality.EXCELLENT,
				"never",
				"notes"
		);

		ForageMapItemDTO actualForageMapItemDTO = converterService.toDTO(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						),
						new CustomMarker(
								"1",
								new double[] {0.0, 0.0},
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0},
								""
						),
						ForageQuantity.ABUNDANT,
						ForageQuality.EXCELLENT,
						"never",
						"notes"
				)
		);
		assertEquals(expectedForageMapItemDTO, actualForageMapItemDTO);
	}
}