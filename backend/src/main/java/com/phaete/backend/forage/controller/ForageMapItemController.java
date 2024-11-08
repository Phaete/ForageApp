package com.phaete.backend.forage.controller;

import com.phaete.backend.forage.model.*;
import com.phaete.backend.forage.service.ForageMapItemService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/forageMapItems")
public class ForageMapItemController {

	private final ForageMapItemService forageMapItemService;

	public ForageMapItemController(
			ForageMapItemService forageMapItemService) {
		this.forageMapItemService = forageMapItemService;
	}

	/**
	 * Accepts a post request on the /api/forageMapItems endpoint and creates a new forage map item in the database.
	 * <p>
	 * @param forageMapItemDTO the forage map item to be created
	 * @return the newly created forage map item
	 */
	@PostMapping
	public ForageMapItemDTO createForageMapItem(@RequestBody ForageMapItemDTO forageMapItemDTO) {
		return forageMapItemService.createForageMapItem(forageMapItemDTO);
	}

	/**
	 * Accepts a GET request on the /api/forageMapItems endpoint and retrieves all forage map items.
	 * <p>
	 * @return a map where the key is a boolean indicating validity, and the value is a list of forage map items.
	 */
	@GetMapping
	public Map<Boolean, List<ForageMapItem>> findAllForageMapItems(OAuth2AuthenticationToken authentication)
			throws UserNotFoundException, InvalidAuthenticationException {
		return forageMapItemService.findAll(authentication);
	}

    /**
     * Accepts a GET request on the /api/forageMapItems/{id} endpoint and retrieves the forage map item with the specified id.
     *
     * @param id the id of the forage map item to be retrieved
     * @return the forage map item with the given id converted to a DTO
     * @throws ForageMapItemNotFoundException if no forage map item with the given id was found
     * @throws MarkerNotFoundException if the marker with the id of the forage map item's custom marker was not found
     * @throws ForageWikiItemNotFoundException if the forage wiki item with the id of the forage map item's forage wiki item was not found
     */
	@GetMapping("/{id}")
	public ForageMapItemDTO findForageMapItemById(@PathVariable String id, OAuth2AuthenticationToken authentication)
			throws ForageMapItemNotFoundException, MarkerNotFoundException, ForageWikiItemNotFoundException,
			UserNotFoundException, InvalidAuthenticationException {
		return forageMapItemService.findForageMapItemById(id, authentication);
	}


	/**
	 * Accepts a PUT request on the /api/forageMapItems/{id} endpoint and updates the forage map item with the given id.
	 * <p>
	 * @param id the id of the forage map item to be updated
	 * @param forageMapItemDTO the updated forage map item data
	 * @param authentication  the authentication details of the user
	 * @return the updated forage map item converted to a DTO
	 * @throws ForageMapItemNotFoundException if no forage map item with the given id was found
	 * @throws UserNotFoundException if the user with the authentication details was not found
	 * @throws InvalidAuthenticationException if the user with the authentication details is not authorized to update
	 * 										the forage map item
	 */
	@PutMapping("/{id}")
	public ForageMapItemDTO updateForageMapItem(
			@PathVariable String id,
			@RequestBody ForageMapItemDTO forageMapItemDTO,
			OAuth2AuthenticationToken authentication
	) throws ForageMapItemNotFoundException, UserNotFoundException, InvalidAuthenticationException {
		return forageMapItemService.updateForageMapItem(id, forageMapItemDTO, authentication);
	}



	/**
	 * Accepts a DELETE request on the /api/forageMapItems/{id} endpoint and deletes the forage map item with the given id.
	 * <p>
	 * @param id the id of the forage map item to be deleted
	 * @param authentication the authentication details of the user
	 * @return a message containing the position of the deleted forage map item
	 * @throws ForageMapItemNotFoundException if no forage map item with the given id was found
	 * @throws UserNotFoundException if the user with the authentication details was not found
	 * @throws InvalidAuthenticationException if the user with the authentication details is not authorized to update
	 * 										the forage map item
	 */
	@DeleteMapping("/{id}")
	public String deleteForageMapItem(@PathVariable String id, OAuth2AuthenticationToken authentication)
			throws ForageMapItemNotFoundException, UserNotFoundException, InvalidAuthenticationException {
		return forageMapItemService.deleteForageMapItem(id, authentication);
	}
}
