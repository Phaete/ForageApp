package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.*;
import com.phaete.backend.forage.repository.ForageMapItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequestMapping("/api/forageMapItem")
public class ForageMapItemService {

	private final ForageMapItemRepository forageMapItemRepository;
	private final ConverterService converterService;

	public ForageMapItemService(
			ForageMapItemRepository forageMapItemRepository,
			ConverterService converterService
	) {
		this.forageMapItemRepository = forageMapItemRepository;
		this.converterService = converterService;
	}


	/**
	 * Creates a new forage map item and saves it to the database.
	 *
	 * @param forageMapItemDTO the forage map item to be created
	 * @return the newly created forage map item
	 */
	public ForageMapItemDTO createForageMapItem(ForageMapItemDTO forageMapItemDTO) {
		return converterService.toDTO(
				forageMapItemRepository.save(
						converterService.fromDTO(forageMapItemDTO)
				)
		);
	}


	/**
	 * Retrieves all forage map items from the database.
	 * <p>
	 * @return a list of all forage map items that contain valid customMarkers and forageWikiItems
	 */
	public List<ForageMapItem> findAllForageMapItems() {
		Map<Boolean, List<ForageMapItem>> forageMapItemMap = forageMapItemRepository.findAll()
				.stream()
				.collect(
						Collectors.partitioningBy(
								forageMapItem -> (
										forageMapItem.getForageWikiItem() != null &&
										forageMapItem.getCustomMarker() != null
								)
						)
				);
		return forageMapItemMap.get(true);
	}


	/**
	 * Retrieves a forage map item from the database by its id.
	 *
	 * @param id the id of the forage map item to be retrieved
	 * @return the forage map item with the given id
	 * @throws ForageMapItemNotFoundException      if no forage map item with the given id was found
	 * @throws MarkerNotFoundException            if the marker with the id of the forage map item's custom marker was not found
	 * @throws ForageWikiItemNotFoundException     if the forage wiki item with the id of the forage map item's forage wiki item was not found
	 */
	public ForageMapItemDTO findForageMapItemById(String id)
			throws ForageMapItemNotFoundException, MarkerNotFoundException, ForageWikiItemNotFoundException {
		ForageMapItemDTO forageMapItemDTO = converterService.toDTO(
				forageMapItemRepository.findById(id).orElseThrow(
					() -> new ForageMapItemNotFoundException("Could not find forage map item with the id: " + id)
				)
		);
		if (forageMapItemDTO.forageWikiItem() == null) {
			throw new ForageWikiItemNotFoundException("Could not find marker for forage map item with the id: " + id);
		}
		if (forageMapItemDTO.customMarker() == null) {
			throw new MarkerNotFoundException("Could not find forage wiki item for forage map item with the id: " + id);
		}
		return forageMapItemDTO;
	}
}
