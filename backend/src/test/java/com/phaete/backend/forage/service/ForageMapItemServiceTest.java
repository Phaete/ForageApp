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

	ForageMapItem expectedForageMapItem = new ForageMapItem(
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

	@Test
	void createForageMapItem_expectDTO_onSuccess() {
		when(forageMapItemRepository.save(any(ForageMapItem.class))).thenReturn(
				expectedForageMapItem
		);
		when(idService.generateId()).thenReturn("1");

		ForageMapItemDTO actualForageMapItemDTO = forageMapItemService.createForageMapItem(expectedForageMapItemDTO);
		verify(forageMapItemRepository).save(any(ForageMapItem.class));
		assertEquals(expectedForageMapItemDTO, actualForageMapItemDTO);
	}

	@Test
	void findAllForageMapItems_expectList_onSuccess() {
		List<ForageMapItem> expectedForageMapItems = List.of(
				expectedForageMapItem
		);
		when(forageMapItemRepository.findAll()).thenReturn(expectedForageMapItems);

		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAllForageMapItems();
		verify(forageMapItemRepository).findAll();
		assertEquals(expectedForageMapItems, actualForageMapItems);
	}

	@Test
	void findAllForageMapItems_expectList_excludeItemsWithoutWikiItemInDB() {
		when(forageMapItemRepository.findAll()).thenReturn(
				List.of(
						expectedForageMapItem
				)
		);

		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAllForageMapItems();
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
								ForageQuantity.ABUNDANT,
								ForageQuality.EXCELLENT,
								"never",
								"notes"
						)
				)
		);

		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAllForageMapItems();
		verify(forageMapItemRepository).findAll();
		assertEquals(List.of(), actualForageMapItems);
	}

	@Test
	void findForageMapItemById_expectDTO_onSuccess()
			throws ForageMapItemNotFoundException, MarkerNotFoundException, ForageWikiItemNotFoundException {

		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						expectedForageMapItem
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
								ForageQuantity.ABUNDANT,
								ForageQuality.EXCELLENT,
								"never",
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
}