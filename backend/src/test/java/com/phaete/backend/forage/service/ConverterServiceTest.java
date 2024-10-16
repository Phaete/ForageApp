package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConverterServiceTest {

	IdService idService = mock(IdService.class);

	@Test
	void fromDTO_CustomMarkerDTO_toCustomMarker() {
		CustomMarker expectedCustomMarker = new CustomMarker(
				"1",
				new double[] {0.0, 0.0},
				null,
				null
		);
		when(idService.generateId()).thenReturn("1");

		ConverterService converterService = new ConverterService(idService);

		CustomMarker actualCustomMarker = converterService.fromDTO(
				new CustomMarkerDTO(
						new double[] {0.0, 0.0},
						null,
						null
				)
		);
		assertEquals(expectedCustomMarker, actualCustomMarker);
	}

	@Test
	void toDTO_CustomMarker_toCustomMarkerDTO() {
		CustomMarkerDTO expectedCustomMarkerDTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				null,
				null
		);

		ConverterService converterService = new ConverterService(idService);

		CustomMarkerDTO actualCustomMarkerDTO = converterService.toDTO(
				new CustomMarker(
						"1",
						new double[] {0.0, 0.0},
						null,
						null
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
				"Autumn",
				List.of("test")
		);
		when(idService.generateId()).thenReturn("1");

		ConverterService converterService = new ConverterService(idService);

		ForageWikiItem actualForageWikiItem = converterService.fromDTO(
				new ForageWikiItemDTO(
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						"Autumn",
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
				"Autumn",
				List.of("test")
		);

		ConverterService converterService = new ConverterService(idService);

		ForageWikiItemDTO actualForageWikiItemDTO = converterService.toDTO(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						"Autumn",
						List.of("test")
				)
		);
		assertEquals(expectedForageWikiItemDTO, actualForageWikiItemDTO);
	}
}