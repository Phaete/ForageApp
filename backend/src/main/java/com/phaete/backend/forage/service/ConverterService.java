package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.*;
import com.phaete.backend.forage.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Converter for conversion between DTOs and entities.
 * <p>
 * @author -St4n aka Phaete
 */
@Service
public class ConverterService {

	private final IdService idService;
	private final UserRepository userRepository;

	public ConverterService(IdService idService, UserRepository userRepository) {
		this.idService = idService;
		this.userRepository = userRepository;
	}

	/**
	 * Converts a {@link CustomMarkerDTO} to a {@link CustomMarker} with a random ID.
	 *
	 * @param customMarkerDTO the {@link CustomMarkerDTO} to be converted
	 * @return the converted {@link CustomMarker}
	 */
	public CustomMarker fromDTO(CustomMarkerDTO customMarkerDTO) {
		return new CustomMarker(
				idService.generateId(),
				customMarkerDTO.name(),
				customMarkerDTO.iconUrl(),
				customMarkerDTO.iconSize(),
				customMarkerDTO.iconAnchor(),
				customMarkerDTO.popupAnchor()
		);
	}

	/**
	 * Converts a {@link CustomMarker} to a {@link CustomMarkerDTO}.
	 *
	 * @param customMarker the {@link CustomMarker} to be converted
	 * @return the converted {@link CustomMarkerDTO}
	 */
	public CustomMarkerDTO toDTO(CustomMarker customMarker){
		return new CustomMarkerDTO(
				customMarker.name(),
				customMarker.iconUrl(),
				customMarker.iconSize(),
				customMarker.iconAnchor(),
				customMarker.popupAnchor()
		);
	}

	/**
	 * Converts a {@link ForageWikiItemDTO} to a {@link ForageWikiItem} with a random ID.
	 *
	 * @param forageWikiItemDTO the {@link ForageWikiItemDTO} to be converted
	 * @return the converted {@link ForageWikiItem}
	 */
	public ForageWikiItem fromDTO(ForageWikiItemDTO forageWikiItemDTO) {
		return new ForageWikiItem(
				idService.generateId(),
				forageWikiItemDTO.name(),
				forageWikiItemDTO.category(),
				forageWikiItemDTO.source(),
				forageWikiItemDTO.description(),
				forageWikiItemDTO.season(),
				forageWikiItemDTO.imageURLs()

		);
	}

	/**
	 * Converts a {@link ForageWikiItem} to a {@link ForageWikiItemDTO}.
	 *
	 * @param forageWikiItem the {@link ForageWikiItem} to be converted
	 * @return the converted {@link ForageWikiItemDTO}
	 */
	public ForageWikiItemDTO toDTO(ForageWikiItem forageWikiItem) {
		return new ForageWikiItemDTO(
				forageWikiItem.name(),
				forageWikiItem.category(),
				forageWikiItem.source(),
				forageWikiItem.description(),
				forageWikiItem.season(),
				forageWikiItem.imageURLs()
		);
	}

	/**
	 * Converts a {@link ForageMapItemDTO} to a {@link ForageMapItem} with a random ID.
	 * <p>
	 * @param forageMapItemDTO the {@link ForageMapItemDTO} to be converted
	 * @return the converted {@link ForageMapItem}
	 */
	public ForageMapItem fromDTO(ForageMapItemDTO forageMapItemDTO) {
		return new ForageMapItem(
				idService.generateId(),
				forageMapItemDTO.forageWikiItem(),
				forageMapItemDTO.customMarker(),
				forageMapItemDTO.position(),
				forageMapItemDTO.ownership(),
				forageMapItemDTO.assessment(),
				forageMapItemDTO.notes());
	}

	/**
	 * Converts a {@link ForageMapItem} to a {@link ForageMapItemDTO}.
	 *
	 * @param forageMapItem the {@link ForageMapItem} to be converted
	 * @return the converted {@link ForageMapItemDTO}
	 */
	public ForageMapItemDTO toDTO(ForageMapItem forageMapItem) {
		return new ForageMapItemDTO(
				forageMapItem.forageWikiItem(),
				forageMapItem.customMarker(),
				forageMapItem.position(),
				forageMapItem.ownership(),
				forageMapItem.assessment(),
				forageMapItem.notes());
	}

	/**
	 * Converts a {@link UserDTO} to a {@link User} with a random ID.
	 * <p>
	 *
	 * @param userDTO the {@link UserDTO} to be converted
	 * @return the converted {@link User}
	 */
	public User fromDTO(UserDTO userDTO) {
		return new User(
				idService.generateId(),
				userDTO.origin(),
				userDTO.name(),
				userDTO.email(),
				userDTO.imageUrl(),
				userRepository.findByOrigin(userDTO.origin()).map(User::role).orElse(Role.USER)
		);
	}

	/**
	 * Converts a {@link User} to a {@link UserDTO}.
	 * <p>
	 * @param user the {@link User} to be converted
	 * @return the converted {@link UserDTO}
	 */
	public UserDTO toDTO(User user) {
		return new UserDTO(
				user.origin(),
				user.name(),
				user.email(),
				user.imageUrl()
		);
	}
}
