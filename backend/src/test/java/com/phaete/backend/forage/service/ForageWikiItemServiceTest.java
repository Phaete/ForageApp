package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.*;
import com.phaete.backend.forage.repository.ForageWikiItemRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ForageWikiItemServiceTest {

	private final ForageWikiItemRepository forageWikiItemRepository = mock(ForageWikiItemRepository.class);
	private final IdService idService = mock(IdService.class);

	@Test
	void createForageWikiItem() {
		ForageWikiItemDTO expectedForageWikiItemDTO = new ForageWikiItemDTO(
				"Apple Tree",
				ForageCategory.FRUIT,
				ForageSource.TREE,
				"Apple Tree",
				ForageSeason.FALL,
				List.of("test")
		);
		when(forageWikiItemRepository.save(any(ForageWikiItem.class))).thenReturn(
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
		when(idService.generateId()).thenReturn("1");

		ForageWikiItemService forageWikiItemService = new ForageWikiItemService(
				forageWikiItemRepository,
				new ConverterService(idService)
		);

		ForageWikiItemDTO actualForageWikiItemDTO = forageWikiItemService.createForageWikiItem(
				expectedForageWikiItemDTO
		);
		verify(forageWikiItemRepository).save(any(ForageWikiItem.class));
		assertEquals(expectedForageWikiItemDTO, actualForageWikiItemDTO);
	}

	@Test
	void findAllForageWikiItems() {
		List<ForageWikiItem> expectedForageWikiItemDTOs = List.of(
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
		when(forageWikiItemRepository.findAll()).thenReturn(
				List.of(
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						)
				)
		);

		ForageWikiItemService forageWikiItemService = new ForageWikiItemService(
				forageWikiItemRepository,
				new ConverterService(idService)
		);

		List<ForageWikiItem> actualForageWikiItemDTOs = forageWikiItemService.findAllForageWikiItems();
		verify(forageWikiItemRepository).findAll();
		assertEquals(expectedForageWikiItemDTOs, actualForageWikiItemDTOs);
	}

	@Test
	void findForageWikiItemById() throws ForageWikiItemNotFoundException {
		ForageWikiItemDTO expectedForageWikiItemDTO = new ForageWikiItemDTO(
			"Apple Tree",
			ForageCategory.FRUIT,
			ForageSource.TREE,
			"Apple Tree",
				ForageSeason.FALL,
			List.of("test")
		);
		when(forageWikiItemRepository.findById("1")).thenReturn(
				Optional.of(new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("test")
				))
		);

		ForageWikiItemService forageWikiItemService = new ForageWikiItemService(
			forageWikiItemRepository,
			new ConverterService(idService)
		);

		ForageWikiItemDTO actualForageWikiItemDTO = forageWikiItemService.findForageWikiItemById("1");
		verify(forageWikiItemRepository).findById("1");
		assertEquals(expectedForageWikiItemDTO, actualForageWikiItemDTO);
	}

	@Test
	void findForageWikiItemById_Throws() {
		when(forageWikiItemRepository.findById("1")).thenReturn(Optional.empty());

		ForageWikiItemService forageWikiItemService = new ForageWikiItemService(
			forageWikiItemRepository,
			new ConverterService(idService)
		);

		assertThrows(ForageWikiItemNotFoundException.class, () -> forageWikiItemService.findForageWikiItemById("1"));
	}

	@Test
	void updateForageWikiItem() throws ForageWikiItemNotFoundException {
		ForageWikiItemDTO expectedForageWikiItemDTO = new ForageWikiItemDTO(
			"Apple Tree",
			ForageCategory.FRUIT,
			ForageSource.TREE,
			"expected Apple Tree",
			ForageSeason.FALL,
			List.of("test")
		);
		when(forageWikiItemRepository.findById("1")).thenReturn(Optional.of(new ForageWikiItem(
			"1",
			"Apple Tree",
			ForageCategory.FRUIT,
			ForageSource.TREE,
			"Apple Tree",
			ForageSeason.FALL,
			List.of("test")
		)));
		when(forageWikiItemRepository.save(any(ForageWikiItem.class))).thenReturn(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"expected Apple Tree",
						ForageSeason.FALL,
						List.of("test")
				)
		);

		ForageWikiItemService forageWikiItemService = new ForageWikiItemService(
			forageWikiItemRepository,
			new ConverterService(idService)
		);

		ForageWikiItemDTO actualForageWikiItemDTO = forageWikiItemService.updateForageWikiItem(
				"1",
				expectedForageWikiItemDTO
		);
		verify(forageWikiItemRepository).save(any(ForageWikiItem.class));
		assertEquals(expectedForageWikiItemDTO, actualForageWikiItemDTO);
	}

	@Test
	void updateForageWikiItem_Throws() {
		when(forageWikiItemRepository.findById("1")).thenReturn(Optional.empty());

		ForageWikiItemService forageWikiItemService = new ForageWikiItemService(
			forageWikiItemRepository,
			new ConverterService(idService)
		);

		assertThrows(ForageWikiItemNotFoundException.class, () -> forageWikiItemService.updateForageWikiItem(
				"1",
				new ForageWikiItemDTO(
					"Apple Tree",
					ForageCategory.FRUIT,
					ForageSource.TREE,
					"expected Apple Tree",
					ForageSeason.FALL,
					List.of("test")
				)
		));
	}

	@Test
	void deleteForageWikiItem() throws ForageWikiItemNotFoundException {
		when(forageWikiItemRepository.findById("1")).thenReturn(Optional.of(new ForageWikiItem(
				"1",
				"Apple Tree",
				ForageCategory.FRUIT,
				ForageSource.TREE,
				"Apple Tree",
				ForageSeason.FALL,
				List.of("test")
		)));

		ForageWikiItemService forageWikiItemService = new ForageWikiItemService(
				forageWikiItemRepository,
				new ConverterService(idService)
		);

		String actualForageWikiItemName = forageWikiItemService.deleteForageWikiItem("1");
		verify(forageWikiItemRepository).deleteById("1");
		assertEquals("Apple Tree", actualForageWikiItemName);
	}

	@Test
	void deleteForageWikiItem_Throws() {
		when(forageWikiItemRepository.findById("1")).thenReturn(Optional.empty());

		ForageWikiItemService forageWikiItemService = new ForageWikiItemService(
				forageWikiItemRepository,
				new ConverterService(idService)
		);

		assertThrows(ForageWikiItemNotFoundException.class, () -> forageWikiItemService.deleteForageWikiItem("1"));
	}
}