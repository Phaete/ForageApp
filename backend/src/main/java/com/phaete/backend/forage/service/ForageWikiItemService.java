package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.ForageWikiItem;
import com.phaete.backend.forage.model.ForageWikiItemDTO;
import com.phaete.backend.forage.model.ForageWikiItemNotFoundException;
import com.phaete.backend.forage.repository.ForageWikiItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing {@link ForageWikiItem}.
 * It provides methods to interact with the {@link ForageWikiItemRepository}
 * allowing for CRUD operations on ForageWikiItem objects.
 * <p>
 * Supported functions as per CRUD:
 * <ul>
 * 	<li> Creating new custom markers </li>
 * 	<li> Retrieving all custom markers </li>
 * 	<li> Retrieving custom markers by id </li>
 * 	<li> Updating custom markers by id </li>
 * 	<li> Deleting custom markers by id </li>
 * </ul>
 * <p>
 * The service acts as a bridge between the controller and the repository
 * and contains all business logic for the ForageWikiItem objects.
 *
 * @author -St4n aka Phaete
 */
@Service
public class ForageWikiItemService {

	private final ForageWikiItemRepository forageWikiItemRepository;
	private final ConverterService converterService;

	public ForageWikiItemService(
			ForageWikiItemRepository forageWikiItemRepository,
			ConverterService converterService) {
		this.forageWikiItemRepository = forageWikiItemRepository;
		this.converterService = converterService;
	}

	/**
	 * Creates a new forage wiki item and saves it to the database.
	 *
	 * @param forageWikiItemDTO the ForageWikiItemDTO to be converted and saved as a ForageWikiItem
	 * @return the newly created ForageWikiItem converted to a DTO
	 */
	public ForageWikiItemDTO createForageWikiItem(ForageWikiItemDTO forageWikiItemDTO) {
		return converterService.toDTO(
				forageWikiItemRepository.save(
						converterService.fromDTO(
								forageWikiItemDTO
						)
				)
		);
	}

	/**
	 * Retrieves all forage wiki items from the database.
	 *
	 * @return a list of all forage wiki items converted to DTOs
	 */
	public List<ForageWikiItem> findAllForageWikiItems() {
		return forageWikiItemRepository.findAll();
	}

	/**
	 * Retrieves a forage wiki item from the database by its id.
	 *
	 * @param id the id of the forage wiki item to be retrieved
	 * @return the forage wiki item converted to a DTO
	 * @throws ForageWikiItemNotFoundException if no forage wiki item with the given id was found
	 */
	public ForageWikiItemDTO findForageWikiItemById(String id) throws ForageWikiItemNotFoundException {
		return converterService.toDTO(
				forageWikiItemRepository.findById(id)
						.orElseThrow(
								() -> new ForageWikiItemNotFoundException("Could not find forage wiki item with the id: " + id))
						);
	}

	/**
	 * Updates an existing forage wiki item in the database.
	 *
	 * @param id the id of the forage wiki item to be updated
	 * @param forageWikiItemDTO the updated forage wiki item data
	 * @return the updated forage wiki item converted to a DTO
	 * @throws ForageWikiItemNotFoundException if no forage wiki item with the given id was found
	 */
	public ForageWikiItemDTO updateForageWikiItem(
			String id,
			ForageWikiItemDTO forageWikiItemDTO
	) throws ForageWikiItemNotFoundException {
		findForageWikiItemById(id);
		return converterService.toDTO(
				forageWikiItemRepository.save(
						new ForageWikiItem(
								id,
								forageWikiItemDTO.name(),
								forageWikiItemDTO.category(),
								forageWikiItemDTO.source(),
								forageWikiItemDTO.description(),
								forageWikiItemDTO.season(),
								forageWikiItemDTO.imageURLs()
						)
				)
		);
	}

	public String deleteForageWikiItem(String id) throws ForageWikiItemNotFoundException {
		String name = findForageWikiItemById(id).name();
		forageWikiItemRepository.deleteById(id);
		return name;
	}
}
