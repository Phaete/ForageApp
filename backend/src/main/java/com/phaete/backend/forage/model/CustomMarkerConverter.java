package com.phaete.backend.forage.model;

import com.phaete.backend.forage.service.IdService;
import org.springframework.stereotype.Service;

/**
 * Converter for {@link CustomMarkerDTO} and {@link CustomMarker} objects.
 * <p>
 * The conversion is done by the {@link #fromDTO(CustomMarkerDTO)} and
 * {@link #toDTO(CustomMarker)} methods.
 */
@Service
public class CustomMarkerConverter {

	private final IdService idService;

	public CustomMarkerConverter(IdService idService) {
		this.idService = idService;
	}

	/**
	 * Converts a {@link CustomMarkerDTO} to a {@link CustomMarker}.
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
	public CustomMarkerDTO toDTO(CustomMarker customMarker) {
		return new CustomMarkerDTO(
				customMarker.position(),
				customMarker.icon(),
				customMarker.popupText()
		);
	}
}
