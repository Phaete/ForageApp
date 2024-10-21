package com.phaete.backend.forage.controller;

import com.phaete.backend.forage.model.ForageWikiItem;
import com.phaete.backend.forage.model.ForageWikiItemDTO;
import com.phaete.backend.forage.model.ForageWikiItemNotFoundException;
import com.phaete.backend.forage.service.ForageWikiItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forageWikiItems")
public class ForageWikiItemController {
	private final ForageWikiItemService forageWikiItemService;

	public ForageWikiItemController(ForageWikiItemService forageWikiItemService) {
		this.forageWikiItemService = forageWikiItemService;
	}

	/**
	 * Accepts a post request on the /api/forageWikiItems endpoint and creates a new forage wiki item in the database.
	 *
	 * @param forageWikiItemDTO the forage wiki item to be created
	 * @return the newly created forage wiki item
	 */
	@PostMapping
	public ForageWikiItemDTO createForageWikiItem(@RequestBody ForageWikiItemDTO forageWikiItemDTO) {
		return forageWikiItemService.createForageWikiItem(forageWikiItemDTO);
	}


	/**
	 * Accepts a get request on the /api/forageWikiItems endpoint and returns a list of all forage wiki items.
	 *
	 * @return a list of all forage wiki items
	 */
	@GetMapping
	public List<ForageWikiItem> findAllForageWikiItems() {
		return forageWikiItemService.findAllForageWikiItems();
	}

	/**
	 * Accepts a get request on the /api/forageWikiItems/{id} endpoint and returns the forage wiki item with the given id.
	 *
	 * @param id the id of the forage wiki item to be retrieved
	 * @return the forage wiki item with the given id
	 * @throws ForageWikiItemNotFoundException if no forage wiki item with the given id was found
	 */
	@GetMapping("/{id}")
	public ForageWikiItemDTO findForageWikiItemById(@PathVariable String id) throws ForageWikiItemNotFoundException {
		return forageWikiItemService.findForageWikiItemById(id);
	}

	/**
	 * Accepts a put request on the /api/forageWikiItems/{id} endpoint and updates the forage wiki item with the given id.
	 *
	 * @param id the id of the forage wiki item to be updated
	 * @param forageWikiItemDTO the updated forage wiki item data
	 * @return the updated forage wiki item converted to a DTO
	 * @throws ForageWikiItemNotFoundException if no forage wiki item with the given id was found
	 */
	@PutMapping("/{id}")
	public ForageWikiItemDTO updateForageWikiItem(@PathVariable String id, @RequestBody ForageWikiItemDTO forageWikiItemDTO) throws ForageWikiItemNotFoundException {
		return forageWikiItemService.updateForageWikiItem(id, forageWikiItemDTO);
	}

	/**
	 * Accepts a delete request on the /api/forageWikiItems/{id} endpoint and deletes the forage wiki item with the given id.
	 *
	 * @param id the id of the forage wiki item to be deleted
	 * @return the deleted forage wiki item as a string
	 * @throws ForageWikiItemNotFoundException if no forage wiki item with the given id was found
	 */
	@DeleteMapping("/{id}")
	public String deleteForageWikiItem(@PathVariable String id) throws ForageWikiItemNotFoundException {
		return forageWikiItemService.deleteForageWikiItem(id);
	}

	/**
	 * Handles {@link ForageWikiItemNotFoundException}s by returning the exception message with a
	 * {@link HttpStatus#NOT_FOUND} status.
	 *
	 * @param e the exception to be handled
	 * @return the exception message
	 */
	@ExceptionHandler(ForageWikiItemNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleForageWikiItemNotFoundException(ForageWikiItemNotFoundException e) {
		return e.getMessage();
	}
}
