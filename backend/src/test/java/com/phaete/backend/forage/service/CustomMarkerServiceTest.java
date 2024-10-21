package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.CustomMarker;
import com.phaete.backend.forage.model.CustomMarkerDTO;
import com.phaete.backend.forage.model.MarkerNotFoundException;
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
	void createMarker_expectDTO_onSuccess() {
		CustomMarkerDTO expectedCustomMarkerDTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				"",
				new int[] {0, 0},
				new int[] {0, 0},
				new int[] {0, 0},
				""
		);
		when(customMarkerRepository.save(any(CustomMarker.class))).thenReturn(
				new CustomMarker(
						"1",
						new double[] {0.0, 0.0},
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0},
						"")
		);
		when(idService.generateId()).thenReturn("1");

		CustomMarkerService customMarkerService = new CustomMarkerService(customMarkerRepository, new ConverterService(idService));

		CustomMarkerDTO actualCustomMarkerDTO = customMarkerService.createMarker(expectedCustomMarkerDTO);
		verify(customMarkerRepository).save(any(CustomMarker.class));
		assertEquals(expectedCustomMarkerDTO, actualCustomMarkerDTO);
	}

	@Test
	void findAllMarkers_expectList_onSuccess() {
		List<CustomMarker> expectedCustomMarkers = List.of(
				new CustomMarker(
						"1",
						new double[] {0.0, 0.0},
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0},
						""
				)
		);
		when(customMarkerRepository.findAll()).thenReturn(
				List.of(new CustomMarker(
						"1",
						new double[] {0.0, 0.0},
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0},
						""
				))
		);

		CustomMarkerService customMarkerService = new CustomMarkerService(customMarkerRepository, new ConverterService(idService));

		List<CustomMarker> actualCustomMarkers = customMarkerService.findAllMarkers();
		verify(customMarkerRepository).findAll();
		assertEquals(expectedCustomMarkers, actualCustomMarkers);
	}

	@Test
	void findMarkerById_expectDTO_onSuccess() throws MarkerNotFoundException {
		CustomMarkerDTO expectedCustomMarkerDTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				"",
				new int[] {0, 0},
				new int[] {0, 0},
				new int[] {0, 0},
				""
		);
		when(customMarkerRepository.findById("1")).thenReturn(
				Optional.of(new CustomMarker(
						"1",
						new double[] {0.0, 0.0},
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0},
						""
				))
		);

		CustomMarkerService customMarkerService = new CustomMarkerService(customMarkerRepository, new ConverterService(idService));

		CustomMarkerDTO actualCustomMarkerDTO = customMarkerService.findMarkerById("1");
		verify(customMarkerRepository).findById("1");
		assertEquals(expectedCustomMarkerDTO, actualCustomMarkerDTO);
	}

	@Test
	void findMarkerById_expectThrows_onNotFound() {
		when(customMarkerRepository.findById("1")).thenReturn(Optional.empty());

		CustomMarkerService customMarkerService = new CustomMarkerService(customMarkerRepository, new ConverterService(idService));

		assertThrows(MarkerNotFoundException.class, () -> customMarkerService.findMarkerById("1"));
	}

	@Test
	void updateMarker_expectDTO_onSuccess() throws MarkerNotFoundException {
		CustomMarkerDTO expectedCustomMarkerDTO = new CustomMarkerDTO(
				new double[] {0.0, 0.0},
				"",
				new int[] {0, 0},
				new int[] {0, 0},
				new int[] {0, 0},
				""
		);
		when(customMarkerRepository.findById("1")).thenReturn(
				Optional.of(new CustomMarker(
						"1",
						new double[] {0.0, 0.0},
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0},
						""
				))
		);
		when(customMarkerRepository.save(any(CustomMarker.class))).thenReturn(
				new CustomMarker(
						"1",
						new double[] {0.0, 0.0},
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0},
						""
				)
		);

		CustomMarkerService customMarkerService = new CustomMarkerService(customMarkerRepository, new ConverterService(idService));

		CustomMarkerDTO actualCustomMarkerDTO = customMarkerService.updateMarker("1", expectedCustomMarkerDTO);
		verify(customMarkerRepository).save(any(CustomMarker.class));
		assertEquals(expectedCustomMarkerDTO, actualCustomMarkerDTO);
	}

	@Test
	void updateMarker_expectThrows_onNotFound() {
		when(customMarkerRepository.findById("1")).thenReturn(Optional.empty());

		CustomMarkerService customMarkerService = new CustomMarkerService(customMarkerRepository, new ConverterService(idService));

		assertThrows(MarkerNotFoundException.class, () -> customMarkerService.updateMarker(
				"1",
				new CustomMarkerDTO(
						new double[] {0.0, 0.0},
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0},
						""
				)
		));
	}

	@Test
	void deleteMarker_expectPositionOfDeletedMarker_onSuccess() throws MarkerNotFoundException {
		String expectedPosition = Arrays.toString(new double[]{0.0, 0.0});
		when(customMarkerRepository.findById("1")).thenReturn(
				Optional.of(new CustomMarker(
						"1",
						new double[] {0.0, 0.0},
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0},
						""
				))
		);

		CustomMarkerService customMarkerService = new CustomMarkerService(customMarkerRepository, new ConverterService(idService));

		String actualPosition = customMarkerService.deleteMarker("1");
		verify(customMarkerRepository).deleteById("1");
		assertEquals(expectedPosition, actualPosition);
	}

	@Test
	void deleteMarker_expectThrows_onNotFound() {
		when(customMarkerRepository.findById("1")).thenReturn(Optional.empty());

		CustomMarkerService customMarkerService = new CustomMarkerService(customMarkerRepository, new ConverterService(idService));

		assertThrows(MarkerNotFoundException.class, () -> customMarkerService.deleteMarker("1"));
	}
}