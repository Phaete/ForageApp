package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.*;
import com.phaete.backend.forage.repository.ForageMapItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for managing {@link ForageMapItem}.
 * It provides methods to interact with the {@link ForageMapItemRepository}
 * allowing for CRUD operations on ForageMapItem objects.
 * <p>
 * Supported functions as per CRUD:
 * <ul>
 *  <li> Creating new forage map items </li>
 *  <li> Retrieving all forage map items </li>
 *  <li> Retrieving forage map items by id </li>
 *  <li> Updating forage map items by id </li>
 *  <li> Deleting forage map items by id </li>
 * </ul>
 * <p>
 * The service acts as a bridge between the controller and the repository
 * and contains all business logic for the ForageMapItem objects.
 * <p>
 * @author -St4n aka Phaete
 */
@Service
@RequestMapping("/api/forageMapItem")
public class ForageMapItemService {

	private final ForageMapItemRepository forageMapItemRepository;
	private final ConverterService converterService;
	private final UserService userService;

	private final UserDTO anonymousUser = new UserDTO(
			"anonymous",
			"anonymous",
			"email",
			"https://circumicons.com/icon/user"
	);

	private static final String FORAGE_MAP_ITEM_NOT_FOUND_MESSAGE = "Could not find forage map item with the id: ";

	private static final Logger logger = LoggerFactory.getLogger(ForageMapItemService.class);

	public ForageMapItemService(
			ForageMapItemRepository forageMapItemRepository,
			ConverterService converterService,
			UserService userService) {
		this.forageMapItemRepository = forageMapItemRepository;
		this.converterService = converterService;
		this.userService = userService;
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
	 * Log all invalid ForageMapItems and the reason they are invalid to the logger.
	 *
	 * @param invalidForageMapItems the list of invalid ForageMapItems
	 */
	private void logInvalidForageMapItems(List<ForageMapItem> invalidForageMapItems) {
		invalidForageMapItems.forEach(
				invalidForageMapItem -> {
					if (invalidForageMapItem.forageWikiItem() == null) {
						logger.warn("ForageMapItem with id {} is missing the forageWikiItem", invalidForageMapItem.id());
					}
					if (invalidForageMapItem.customMarker() == null) {
						logger.warn("ForageMapItem with id {} is missing the customMarker", invalidForageMapItem.id());
					}
					logger.warn("Invalid ForageMapItem details: {}", invalidForageMapItem);
				}
		);
	}

	/**
	 * Retrieves all forage map items from the database.
	 * <p>
	 * @return a map of all forage map items that are split into valid and invalid map items
	 */
	private Map<Boolean, List<ForageMapItem>> findAllForageMapItems() {
		Map<Boolean, List<ForageMapItem>> forageMapItemMap = forageMapItemRepository.findAll()
				.stream()
				.collect(
						Collectors.partitioningBy(
								forageMapItem -> (
										forageMapItem.forageWikiItem() != null &&
										forageMapItem.customMarker() != null
								)
						)
				);
		logInvalidForageMapItems(forageMapItemMap.get(false));
		return forageMapItemMap;
	}

	/**
	 * Retrieves all forage map items from the database that are available to a user.
 	 * Available to the user are all forageMapItems that are owned by the user or are public.
	 * <p>
	 * @param currentUser the current user
	 * @return a map of all forage map items that are split into valid and invalid map items
	 */
	private Map<Boolean, List<ForageMapItem>> findAllForageMapItemsAvailableToUser(
			UserDTO currentUser
	) {
		Map<Boolean, List<ForageMapItem>> forageMapItemMap = forageMapItemRepository.findAll()
				.stream()
				.filter(
						forageMapItem -> forageMapItem.ownership().owner().equals(currentUser.origin()) ||
								forageMapItem.ownership().isPublic()
				)
				.collect(
						Collectors.partitioningBy(
								forageMapItem -> (
										forageMapItem.forageWikiItem() != null &&
										forageMapItem.customMarker() != null
								)
						)
				);
		logInvalidForageMapItems(forageMapItemMap.get(false));
		return forageMapItemMap;
	}

	/**
	 * Retrieves a list of forage map items base on the authentication token of the current user.
	 *
	 * @param authentication the authentication details of the user
	 * @return a map of all forage map items that are split into valid and invalid map items
	 * @throws UserNotFoundException if the user was not found
	 * @throws InvalidAuthenticationException if the authentication details are invalid
	 */
	public Map<Boolean, List<ForageMapItem>> findAll(OAuth2AuthenticationToken authentication)
			throws UserNotFoundException, InvalidAuthenticationException {
		UserDTO currentUser = authentication != null ? userService.getUserByAttributes(authentication.getPrincipal().getAttributes()) : anonymousUser;
		if (userService.getUserRole(
				currentUser.origin()
		) == Role.ADMIN) {
			return findAllForageMapItems();
		} else {
			return findAllForageMapItemsAvailableToUser(currentUser);
		}
	}

	/**
	 * Retrieves a forage map item from the database by its id.
	 *
	 * @param id the id of the forage map item to be retrieved
	 * @param authentication the authentication details of the user
	 * @return the forage map item with the given id
	 * @throws ForageMapItemNotFoundException if no forage map item with the given id was found
	 * @throws MarkerNotFoundException if the marker with the id of the forage map item's custom marker was not found
	 * @throws ForageWikiItemNotFoundException if the forage wiki item with the id of the forage map item's forage wiki item was not found
	 * @throws UserNotFoundException if the user specified in the authentication details was not found
	 * @throws InvalidAuthenticationException if the user specified in the authentication details has no permission to
	 * 										  update the forage map item
	 */
	public ForageMapItemDTO findForageMapItemById(String id, OAuth2AuthenticationToken authentication)
			throws ForageMapItemNotFoundException, MarkerNotFoundException, ForageWikiItemNotFoundException, UserNotFoundException, InvalidAuthenticationException {
		ForageMapItemDTO forageMapItemDTO = converterService.toDTO(
				forageMapItemRepository.findById(id).orElseThrow(
					() -> new ForageMapItemNotFoundException(FORAGE_MAP_ITEM_NOT_FOUND_MESSAGE + id)
				)
		);

		if (forageMapItemDTO.forageWikiItem() == null) {
			throw new ForageWikiItemNotFoundException("Could not find marker for forage map item with the id: " + id);
		}
		if (forageMapItemDTO.customMarker() == null) {
			throw new MarkerNotFoundException("Could not find forage wiki item for forage map item with the id: " + id);
		}

		UserDTO currentUser = authentication != null ?
				userService.getUserByAttributes(authentication.getPrincipal().getAttributes()) : anonymousUser;
		if (
				forageMapItemDTO.ownership().owner().equals(currentUser.origin()) ||
						forageMapItemDTO.ownership().isPublic() ||
						userService.getUserRole(currentUser.origin()).equals(Role.ADMIN)
		) {
			return forageMapItemDTO;
		} else {
			throw new InvalidAuthenticationException("You are not authenticated to view this forage map item.");
		}
	}

	/**
	 * Updates an existing forage map item in the database.
	 *
	 * @param id the id of the forage map item to be updated
	 * @param forageMapItemDTO the updated forage map item data
	 * @param authentication the authentication details of the user
	 * @return the updated forage map item converted to a DTO
	 * @throws ForageMapItemNotFoundException if no forage map item with the given id was found
	 * @throws UserNotFoundException if the user specified in the authentication details was not found
	 * @throws InvalidAuthenticationException if the user specified in the authentication details has no permission to
	 * 										  update the forage map item
	 */
	public ForageMapItemDTO updateForageMapItem(
			String id, ForageMapItemDTO forageMapItemDTO, OAuth2AuthenticationToken authentication
	) throws ForageMapItemNotFoundException, UserNotFoundException, InvalidAuthenticationException {
		UserDTO currentUser = authentication != null ?
				userService.getUserByAttributes(authentication.getPrincipal().getAttributes()) : anonymousUser;
		ForageMapItem forageMapItemToUpdate = forageMapItemRepository.findById(id).orElseThrow(
				() ->new ForageMapItemNotFoundException(FORAGE_MAP_ITEM_NOT_FOUND_MESSAGE + id)
		);
		if (
				(forageMapItemToUpdate.ownership().owner().equals(forageMapItemDTO.ownership().owner()) &&
				forageMapItemDTO.ownership().owner().equals(currentUser.origin()))
				|| userService.getUserRole(currentUser.origin()).equals(Role.ADMIN)
		) {
			return converterService.toDTO(
					forageMapItemRepository.save(
							new ForageMapItem(
									id,
									forageMapItemDTO.forageWikiItem(),
									forageMapItemDTO.customMarker(),
									forageMapItemDTO.position(),
									forageMapItemDTO.ownership(),
									forageMapItemDTO.assessment(),
									forageMapItemDTO.notes())
					)
			);
		} else {
			throw new InvalidAuthenticationException("You are not authenticated to update this forage map item.");
		}
	}

	/**
	 * Deletes a forage map item from the database.
	 * @param id the id of the forage item to be deleted
	 * @param authentication the authentication details of the user
	 * @return a string containing the latitude and longitude of the deleted forage map item's position
	 * @throws ForageMapItemNotFoundException if no forage map item with the given id was found
	 * @throws UserNotFoundException if the user trying to delete the forage map item was not found
	 * @throws InvalidAuthenticationException if the user trying to delete the forage map item does not have the
	 * 										required authority to delete the forage map item
	 */
	public String deleteForageMapItem(String id, OAuth2AuthenticationToken authentication)
			throws ForageMapItemNotFoundException, UserNotFoundException, InvalidAuthenticationException {
		ForageMapItem forageMapItemToDelete = forageMapItemRepository.findById(id)
						.orElseThrow(
								() -> new ForageMapItemNotFoundException(FORAGE_MAP_ITEM_NOT_FOUND_MESSAGE + id)
						);
		UserDTO currentUser = authentication != null ?
				userService.getUserByAttributes(authentication.getPrincipal().getAttributes()) : anonymousUser;
		if (
				forageMapItemToDelete.ownership().owner().equals(currentUser.origin()) ||
						userService.getUserRole(currentUser.origin()).equals(Role.ADMIN)
		) {
			forageMapItemRepository.deleteById(id);
			return forageMapItemToDelete.position().latitude() + ", " + forageMapItemToDelete.position().longitude();
		} else {
			throw new InvalidAuthenticationException("You are not authenticated to delete this forage map item.");
		}
	}
}
