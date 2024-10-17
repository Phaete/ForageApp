package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.*;
import org.springframework.stereotype.Service;

/**
 * Converter for conversion between DTOs and regular objects.
 * <p>
 */
@Service
public class ConverterService {

	private final IdService idService;

	public ConverterService(IdService idService) {
		this.idService = idService;
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
				customMarkerDTO.position(),
				customMarkerDTO.icon(),
				customMarkerDTO.popupText()
		);
	}

	/**
	 * Converts a {@link CustomMarker} to a {@link CustomMarkerDTO}.
	 *
	 * @param customMarker the {@link CustomMarker} to be converted
	 * @return the converted {@link CustomMarkerDTO}
	 */
	public CustomMarkerDTO toDTO(CustomMarker customMarker) throws MarkerNotFoundException {
		return new CustomMarkerDTO(
				customMarker.position(),
				customMarker.icon(),
				customMarker.popupText()
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
}
