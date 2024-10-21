package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.CustomMarker;
import com.phaete.backend.forage.model.CustomMarkerDTO;
import com.phaete.backend.forage.model.MarkerNotFoundException;
import com.phaete.backend.forage.repository.CustomMarkerRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service class for managing custom markers.
 * It provides methods to interact with the custom marker repository
 * allowing for CRUD operations on CustomMarker objects.
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
 * and contains all business logic for the object CustomMarker
 *
 * @author -St4n aka Phaete
 */
@Service
public class CustomMarkerService {

	private final CustomMarkerRepository customMarkerRepository;
	private final ConverterService converterService;

	public CustomMarkerService(
			CustomMarkerRepository customMarkerRepository,
			ConverterService converterService) {
		this.customMarkerRepository = customMarkerRepository;
		this.converterService = converterService;
	}

	/**
	 * Creates a new custom marker and saves it to the database.
	 *
	 * @param customMarkerDTO the CustomMarkerDTO to be converted and saved as a CustomMarker
	 * @return the newly created CustomMarker converted to a DTO
	 */
	public CustomMarkerDTO createMarker(CustomMarkerDTO customMarkerDTO){
		return converterService.toDTO(
				customMarkerRepository.save(
						converterService.fromDTO(
								customMarkerDTO
						)
				)
		);
	}

	/**
	 * Retrieves all custom markers from the database.
	 *
	 * @return a list of all custom markers converted to DTOs
	 */
	public List<CustomMarker> findAllMarkers() {
		return customMarkerRepository.findAll();
	}

	/**
	 * Retrieves a custom marker by its id.
	 *
	 * @param id the id of the custom marker to be found
	 * @return the custom marker with the given id converted to a DTO
	 * @throws MarkerNotFoundException if no custom marker with the given id can be found
	 */
	public CustomMarkerDTO findMarkerById(String id) throws MarkerNotFoundException {
		return converterService.toDTO(
				customMarkerRepository.findById(id)
						.orElseThrow(
								() -> new MarkerNotFoundException("Could not find marker with the id: " + id)
						)
		);
	}

	/**
	 * Updates an existent custom marker in the database.
	 *
	 * @param id the id of the custom marker to be updated
	 * @param customMarkerDTO the CustomMarkerDTO with the updated fields
	 * @return the updated CustomMarker converted to a DTO
	 * @throws MarkerNotFoundException if no custom marker with the given id can be found
	 */
	public CustomMarkerDTO updateMarker(
			String id,
			CustomMarkerDTO customMarkerDTO
	) throws MarkerNotFoundException {
		findMarkerById(id);
		return converterService.toDTO(
				customMarkerRepository.save(
						new CustomMarker(
								id,
								customMarkerDTO.position(),
								customMarkerDTO.iconUrl(),
								customMarkerDTO.iconSize(),
								customMarkerDTO.iconAnchor(),
								customMarkerDTO.popupAnchor(),
								customMarkerDTO.popupText()
						)
				)
		);
	}

	/**
	 * Deletes a custom marker in the database.
	 *
	 * @param id the id of the custom marker to be deleted
	 * @return the position of the deleted custom marker as an array of length 2
	 * @throws MarkerNotFoundException if no custom marker with the given id can be found
	 */
	public String deleteMarker(String id) throws MarkerNotFoundException {
		CustomMarkerDTO customMarkerToDelete = findMarkerById(id);
		customMarkerRepository.deleteById(id);
		return Arrays.toString(customMarkerToDelete.position());
	}
}
