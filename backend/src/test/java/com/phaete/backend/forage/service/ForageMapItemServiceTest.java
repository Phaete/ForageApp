package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.*;
import com.phaete.backend.forage.repository.ForageMapItemRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ForageMapItemServiceTest {

	private final ForageMapItemRepository forageMapItemRepository = mock(ForageMapItemRepository.class);
	private final IdService idService = mock(IdService.class);

	ForageMapItemService forageMapItemService = new ForageMapItemService(
			forageMapItemRepository,
			new ConverterService(idService)
	);

	@Test
	void createForageMapItem_expectDTO_onSuccess() {
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
						"test",
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0}
				),
				new GeoPosition(0.0, 0.0),
				new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
				"notes"
		);

		when(forageMapItemRepository.save(any(ForageMapItem.class))).thenReturn(
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
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes"
				)
		);
		when(idService.generateId()).thenReturn("1");



		ForageMapItemDTO actualForageMapItemDTO = forageMapItemService.createForageMapItem(expectedForageMapItemDTO);
		verify(forageMapItemRepository).save(any(ForageMapItem.class));
		assertEquals(expectedForageMapItemDTO, actualForageMapItemDTO);
	}

	@Test
	void findAllForageMapItems_expectList_onSuccess() {
		List<ForageMapItem> expectedForageMapItems = List.of(
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
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes"
				)
		);
		when(forageMapItemRepository.findAll()).thenReturn(expectedForageMapItems);

		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAllForageMapItems().get(true);
		verify(forageMapItemRepository).findAll();
		assertEquals(expectedForageMapItems, actualForageMapItems);
	}

	@Test
	void findAllForageMapItems_expectList_excludeItemsWithoutWikiItemInDB() {
		when(forageMapItemRepository.findAll()).thenReturn(
				List.of(
						new ForageMapItem(
								"1",
								null,
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(0.0, 0.0),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes"
						)
				)
		);

		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAllForageMapItems().get(true);
		verify(forageMapItemRepository).findAll();
		assertEquals(List.of(), actualForageMapItems);
	}

	@Test
	void findAllForageMapItems_expectList_excludeItemsWithoutCustomMarkerInDB() {
		when(forageMapItemRepository.findAll()).thenReturn(
				List.of(
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
								null,
								new GeoPosition(0.0, 0.0),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes"
						)
				)
		);

		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAllForageMapItems().get(true);
		verify(forageMapItemRepository).findAll();
		assertEquals(List.of(), actualForageMapItems);
	}

	@Test
	void findForageMapItemById_expectDTO_onSuccess()
			throws ForageMapItemNotFoundException, MarkerNotFoundException, ForageWikiItemNotFoundException {
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
						"test",
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0}
				),
				new GeoPosition(0.0, 0.0),
				new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
				"notes"
		);

		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
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
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(0.0, 0.0),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes"
						)
				)
		);

		assertEquals(expectedForageMapItemDTO, forageMapItemService.findForageMapItemById("1"));
	}

	@Test
	void findForageMapItemById_expectThrows_onWikiItemNotFound() {
		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								null,
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(0.0, 0.0),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes"
						)
				)
		);

		assertThrows(ForageWikiItemNotFoundException.class, () -> forageMapItemService.findForageMapItemById("1"));
	}

	@Test
	void findForageMapItemById_expectThrows_onMarkerNotFound() {
		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
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
								null,
								new GeoPosition(0.0, 0.0),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes"
						)
				)
		);

		assertThrows(MarkerNotFoundException.class, () -> forageMapItemService.findForageMapItemById("1"));
	}

	@Test
	void findForageMapItemById_expectThrows_onForageMapItemNotFound() {
		when(forageMapItemRepository.findById("1")).thenReturn(Optional.empty());

		assertThrows(ForageMapItemNotFoundException.class, () -> forageMapItemService.findForageMapItemById("1"));
	}

	@Test
	void updateForageMapItem_expectDTO_onSuccess() throws ForageMapItemNotFoundException {
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
						"test",
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0}
				),
				new GeoPosition(0.0, 0.0),
				new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
				"notes"
		);

		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
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
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(1.0, 1.0),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes"
						)
				)
		);

		when(forageMapItemRepository.save(any(ForageMapItem.class))).thenReturn(
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
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes"
				)
		);

		ForageMapItemDTO actualForageMapItem = forageMapItemService.updateForageMapItem("1", expectedForageMapItemDTO);
		verify(forageMapItemRepository).save(any(ForageMapItem.class));
		assertEquals(expectedForageMapItemDTO, actualForageMapItem);
	}

	@Test
	void updateForageMapItem_expectThrows_onForageMapItemNotFound() {
		when(forageMapItemRepository.findById("1")).thenReturn(Optional.empty());

		assertThrows(ForageMapItemNotFoundException.class, () -> forageMapItemService.updateForageMapItem(
				"1",
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
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes"

				)
		));
	}

	@Test
	void deleteForageMapItem_expectPosition_onSuccess() throws ForageMapItemNotFoundException {
		String expectedPosition = "1.0, 1.0";

		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
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
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(1.0, 1.0),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes"
						)
				)
		);

		String actualPosition = forageMapItemService.deleteForageMapItem("1");
		verify(forageMapItemRepository).deleteById("1");
		assertEquals(expectedPosition, actualPosition);
	}

	@Test
	void deleteForageMapItem_expectStatusNotFound_onEmptyDB() {
		when(forageMapItemRepository.findById("1")).thenReturn(Optional.empty());

		assertThrows(ForageMapItemNotFoundException.class, () -> forageMapItemService.deleteForageMapItem("1"));
	}
}