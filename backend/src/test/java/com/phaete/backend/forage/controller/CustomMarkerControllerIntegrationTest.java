package com.phaete.backend.forage.controller;

import com.phaete.backend.forage.model.CustomMarker;
import com.phaete.backend.forage.repository.CustomMarkerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomMarkerControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CustomMarkerRepository customMarkerRepository;

	@Test
	void saveMarker_expectCreated() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/customMarkers")
				.contentType("application/json")
				.content("""
					{
						"name": "test",
						"position": [0.0, 0.0],
						"iconUrl": "https://example.com/icon.png",
						"iconSize": [32, 32],
						"iconAnchor": [16, 16],
						"popupAnchor": [0, -16],
						"popupText": "Test Marker"
					}
				""")
		)
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"name": "test",
						"position": [0.0, 0.0],
						"iconUrl": "https://example.com/icon.png",
						"iconSize": [32, 32],
						"iconAnchor": [16, 16],
						"popupAnchor": [0, -16],
						"popupText": "Test Marker"
					}
				"""));
	}

	@Test
	void findAllMarkers_returnEmptyList_onEmpty_DB() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/customMarkers"))
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}

	@Test
	void findMarkerById_expectNoSuchElementException_onEmptyDB() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/customMarkers/1"))
				.andExpect(status().isNotFound());
	}

	@Test
	void findMarkerById_expectCustomMarker_onSuccess() throws Exception {
		customMarkerRepository.save(
				new CustomMarker("1", "test", new double[] {0.0, 0.0}, "", new int[] {0, 0}, new int[] {0, 0}, new int[] {0, 0}, "")
		);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/customMarkers/1"))
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"name": "test",
						"position": [0.0, 0.0],
						"iconUrl": "",
						"iconSize": [0, 0],
						"iconAnchor": [0, 0],
						"popupAnchor": [0, 0],
						"popupText": ""
					}
					"""));
	}

	@Test
	void updateMarker_expectOK() throws Exception {
		customMarkerRepository.save(
				new CustomMarker("1", "test", new double[] {0.0, 0.0}, "", new int[] {0, 0}, new int[] {0, 0}, new int[] {0, 0}, "")

		);

		mockMvc.perform(
				MockMvcRequestBuilders.put("/api/customMarkers/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
							{
								"name": "test",
								"position": [0.0, 0.0],
								"iconUrl": "",
								"iconSize": [0, 0],
								"iconAnchor": [0, 0],
								"popupAnchor": [0, 0],
								"popupText": ""
							}
						""")
		)
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"name": "test",
						"position": [0.0, 0.0],
						"iconUrl": "",
						"iconSize": [0, 0],
						"iconAnchor": [0, 0],
						"popupAnchor": [0, 0],
						"popupText": ""
					}
				"""));
	}

	@Test
	void deleteMarker_expectPositionOfMarker_onSuccess() throws Exception {
		customMarkerRepository.save(
				new CustomMarker("1", "test", new double[] {0.0, 0.0}, "", new int[] {0, 0}, new int[] {0, 0}, new int[] {0, 0}, "")

		);

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/customMarkers/1"))
				.andExpect(status().isOk())
				.andExpect(content().string("[0.0, 0.0]"));
	}
}