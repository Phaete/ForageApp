package com.phaete.backend.forage.controller;

import com.phaete.backend.forage.model.CustomMarkerDTO;
import com.phaete.backend.forage.service.CustomMarkerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is a REST controller for the customMarkers endpoint.
 * It is responsible for handling requests to the /api/customMarkers endpoint
 * and properly redirecting them to the CustomMarkerService.
 *
 * @author -St4n aka Phaete
 */
@RestController
@RequestMapping("/api/customMarkers")
public class CustomMarkerController {

	private final CustomMarkerService customMarkerService;

	public CustomMarkerController(CustomMarkerService customMarkerService) {
		this.customMarkerService = customMarkerService;
	}

	/**
	 * Accepts a post request on the /api/customMarkers endpoint and creates a new custom marker in the database.
	 *
	 * @param customMarkerDTO the custom marker to be created
	 * @return the newly created custom marker
	 */
	@PostMapping
	public CustomMarkerDTO createMarker(
			@RequestBody CustomMarkerDTO customMarkerDTO
	) {
		return customMarkerService.createMarker(customMarkerDTO);
	}
	/**
	 * Accepts a get request on the /api/customMarkers endpoint and returns a list of all custom markers.
	 *
	 * @return a list of all custom markers
	 */
	@GetMapping
	public List<CustomMarkerDTO> findAllMarkers() {
		return customMarkerService.findAllMarkers();
	}

	/**
	 * Accepts a get request on the /api/customMarkers/{id} endpoint and returns the custom marker with the given id.
	 *
	 * @param id the id of the custom marker
	 * @return the custom marker with the given id
	 */
	@GetMapping("/{id}")
	public CustomMarkerDTO findMarkerById(@PathVariable String id) {
		return customMarkerService.findMarkerById(id);
	}

	/**
	 * Accepts a put request on the /api/customMarkers/{id} endpoint and updates the custom marker with the given id.
	 *
	 * @param id the id of the custom marker
	 * @param customMarkerDTO the custom marker with the new values
	 * @return the updated custom marker
	 */
	@PutMapping("/{id}")
	public CustomMarkerDTO updateMarker(
			@PathVariable String id,
			@RequestBody CustomMarkerDTO customMarkerDTO
	) {
		return customMarkerService.updateMarker(id, customMarkerDTO);
	}


	/**
	 * Accepts a delete request on the /api/customMarkers/{id} endpoint and deletes the custom marker with the given id.
	 * <p>
	 * The custom marker will be deleted
	 * and the position of the deleted custom marker will be returned as an array of length 2: [latitude, longitude].
	 * <p>
	 * @param id the id of the custom marker
	 * @return the position of the deleted custom marker as an array of length 2
	 */
	@DeleteMapping("/{id}")
	public String deleteMarker(
			@PathVariable String id
	) {
		return customMarkerService.deleteMarker(id);
	}
}
