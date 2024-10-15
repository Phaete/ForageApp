package com.phaete.backend.forage.model;

import com.phaete.backend.forage.service.IdService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomMarkerConverterTest {

	IdService idService = mock(IdService.class);

	@Test
	void fromDTO() {
		CustomMarker expectedCustomMarker = new CustomMarker(
				"1",
				new double[] {0.0, 0.0},
				null,
				null
		);
		when(idService.generateId()).thenReturn("1");

		CustomMarkerConverter customMarkerConverter = new CustomMarkerConverter(idService);

		CustomMarker actualCustomMarker = customMarkerConverter.fromDTO(
				new CustomMarkerDTO(
						new double[] {0.0, 0.0},
						null,
						null
				)
		);
		assertEquals(expectedCustomMarker, actualCustomMarker);
	}

	@Test
	void toDTO() {
		CustomMarkerDTO expectedCustomMarkerDTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				null,
				null
		);

		CustomMarkerConverter customMarkerConverter = new CustomMarkerConverter(idService);

		CustomMarkerDTO actualCustomMarkerDTO = customMarkerConverter.toDTO(
				new CustomMarker(
						"1",
						new double[] {0.0, 0.0},
						null,
						null
				)
		);
		assertEquals(expectedCustomMarkerDTO, actualCustomMarkerDTO);
	}
}