package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.CustomMarker;
import com.phaete.backend.forage.model.CustomMarkerConverter;
import com.phaete.backend.forage.model.CustomMarkerDTO;
import com.phaete.backend.forage.repository.CustomMarkerRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomMarkerServiceTest {

	private final CustomMarkerRepository customMarkerRepository = mock(CustomMarkerRepository.class);
	private final IdService idService = mock(IdService.class);


	@Test
	void createMarker() {
		CustomMarkerDTO expectedCustomMarkerDTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				null,
				null
		);
		when(customMarkerRepository.save(any(CustomMarker.class))).thenReturn(
				new CustomMarker("1", new double[] {0.0, 0.0}, null, null)
		);
		when(idService.generateId()).thenReturn("1");

		CustomMarkerService customMarkerService = new CustomMarkerService(customMarkerRepository, new CustomMarkerConverter(idService));

		CustomMarkerDTO actualCustomMarkerDTO = customMarkerService.createMarker(expectedCustomMarkerDTO);
		verify(customMarkerRepository).save(any(CustomMarker.class));
		assertEquals(expectedCustomMarkerDTO, actualCustomMarkerDTO);
	}

	@Test
	void findAllMarkers() {
		List<CustomMarkerDTO> expectedCustomMarkerDTOs = List.of(
				new CustomMarkerDTO(
						new double[] {0.0, 0.0},
						null,
						null
				)
		);
		when(customMarkerRepository.findAll()).thenReturn(
				List.of(new CustomMarker("1", new double[] {0.0, 0.0}, null, null))
		);

		CustomMarkerService customMarkerService = new CustomMarkerService(customMarkerRepository, new CustomMarkerConverter(idService));

		List<CustomMarkerDTO> actualCustomMarkerDTOs = customMarkerService.findAllMarkers();
		verify(customMarkerRepository).findAll();
		assertEquals(expectedCustomMarkerDTOs, actualCustomMarkerDTOs);
	}

	@Test
	void findMarkerById() {
		CustomMarkerDTO expectedCustomMarkerDTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				null,
				null
		);
		when(customMarkerRepository.findById("1")).thenReturn(
				Optional.of(new CustomMarker("1", new double[] {0.0, 0.0}, null, null))
		);

		CustomMarkerService customMarkerService = new CustomMarkerService(customMarkerRepository, new CustomMarkerConverter(idService));

		CustomMarkerDTO actualCustomMarkerDTO = customMarkerService.findMarkerById("1");
		verify(customMarkerRepository).findById("1");
		assertEquals(expectedCustomMarkerDTO, actualCustomMarkerDTO);
	}

	@Test
	void updateMarker() {
		CustomMarkerDTO expectedCustomMarkerDTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				null,
				null
		);
		when(customMarkerRepository.findById("1")).thenReturn(
				Optional.of(new CustomMarker("1", new double[] {0.0, 0.0}, null, ""))
		);
		when(customMarkerRepository.save(any(CustomMarker.class))).thenReturn(
				new CustomMarker("1", new double[] {0.0, 0.0}, null, null)
		);

		CustomMarkerService customMarkerService = new CustomMarkerService(customMarkerRepository, new CustomMarkerConverter(idService));

		CustomMarkerDTO actualCustomMarkerDTO = customMarkerService.updateMarker("1", expectedCustomMarkerDTO);
		verify(customMarkerRepository).save(any(CustomMarker.class));
		assertEquals(expectedCustomMarkerDTO, actualCustomMarkerDTO);
	}

	@Test
	void deleteMarker() {
		String expectedPosition = Arrays.toString(new double[]{0.0, 0.0});
		when(customMarkerRepository.findById("1")).thenReturn(
				Optional.of(new CustomMarker("1", new double[] {0.0, 0.0}, null, null))
		);

		CustomMarkerService customMarkerService = new CustomMarkerService(customMarkerRepository, new CustomMarkerConverter(idService));

		String actualPosition = customMarkerService.deleteMarker("1");
		verify(customMarkerRepository).deleteById("1");
		assertEquals(expectedPosition, actualPosition);
	}
}