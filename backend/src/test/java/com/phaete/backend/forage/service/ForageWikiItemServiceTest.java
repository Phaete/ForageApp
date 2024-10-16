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
				"Autumn",
				List.of("test")
		);
		when(forageWikiItemRepository.save(any(ForageWikiItem.class))).thenReturn(
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
		when(idService.generateId()).thenReturn("1");

		ForageWikiItemService forageWikiItemService = new ForageWikiItemService(
				forageWikiItemRepository,
				new ConverterService(idService)
		);

		ForageWikiItemDTO actualForageWikiItemDTO = forageWikiItemService.createForageWikiItem(expectedForageWikiItemDTO);
		verify(forageWikiItemRepository).save(any(ForageWikiItem.class));
		assertEquals(expectedForageWikiItemDTO, actualForageWikiItemDTO);
	}

	@Test
	void findAllForageWikiItems() {
		List<ForageWikiItemDTO> expectedForageWikiItemDTOs = List.of(
				new ForageWikiItemDTO(
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						"Autumn",
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
								"Autumn",
								List.of("test")
						)
				)
		);

		ForageWikiItemService forageWikiItemService = new ForageWikiItemService(
				forageWikiItemRepository,
				new ConverterService(idService)
		);

		List<ForageWikiItemDTO> actualForageWikiItemDTOs = forageWikiItemService.findAllForageWikiItems();
		verify(forageWikiItemRepository).findAll();
		assertEquals(expectedForageWikiItemDTOs, actualForageWikiItemDTOs);
	}

	@Test
	void findForageWikiItemById() {
		ForageWikiItemDTO expectedForageWikiItemDTO = new ForageWikiItemDTO(
			"Apple Tree",
			ForageCategory.FRUIT,
			ForageSource.TREE,
			"Apple Tree",
			"Autumn",
			List.of("test")
		);
		when(forageWikiItemRepository.findById("1")).thenReturn(
				Optional.of(new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						"Autumn",
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
}