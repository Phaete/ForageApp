package com.phaete.backend.forage.controller;

import com.phaete.backend.forage.model.*;
import com.phaete.backend.forage.service.ForageMapItemService;
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

	@GetMapping
	public Map<Boolean, List<ForageMapItem>> findAllForageMapItems() {
		return forageMapItemService.findAllForageMapItems();
	}

	@GetMapping("/{id}")
	public ForageMapItemDTO findForageMapItemById(@PathVariable String id)
			throws ForageMapItemNotFoundException, MarkerNotFoundException, ForageWikiItemNotFoundException {
		return forageMapItemService.findForageMapItemById(id);
	}
}
