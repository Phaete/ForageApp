package com.phaete.backend.forage.controller;

import com.phaete.backend.forage.model.*;
import com.phaete.backend.forage.repository.CustomMarkerRepository;
import com.phaete.backend.forage.repository.ForageMapItemRepository;
import com.phaete.backend.forage.repository.ForageWikiItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ForageMapItemControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ForageMapItemRepository forageMapItemRepository;

	@Autowired
	private CustomMarkerRepository customMarkerRepository;

	@Autowired
	private ForageWikiItemRepository forageWikiItemRepository;

	@Test
	void createForageMapItem_expectCreated() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/forageMapItems")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"forageWikiItem": {
							"id": "1",
							"name": "Apple",
							"category": "FRUIT",
							"source": "TREE",
							"description": "Apple from an Apple Tree",
							"season": "FALL",
							"imageURLs": [
							  "url-to-apple-tree-image"
							]
						},
						"customMarker": {
							"id": "1",
							"position": [
								51.0,
								10.0
							],
							"iconUrl": "test-icon",
							"iconSize": [
								64,
								64
							],
							"iconAnchor": [
								32,
								64
							],
							"popupAnchor": [
								0,
								64
							],
							"popupText": "Test popup"
						},
						"quantity": "ABUNDANT",
						"quality": "EXCELLENT",
						"dateFound": "never",
						"notes": "test"
					}
				""")
		)
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"forageWikiItem": {
							"id": "1",
							"name": "Apple",
							"category": "FRUIT",
							"source": "TREE",
							"description": "Apple from an Apple Tree",
							"season": "FALL",
							"imageURLs": [
							  "url-to-apple-tree-image"
							]
						},
						"customMarker": {
							"id": "1",
							"position": [
								51.0,
								10.0
							],
							"iconUrl": "test-icon",
							"iconSize": [
								64,
								64
							],
							"iconAnchor": [
								32,
								64
							],
							"popupAnchor": [
								0,
								64
							],
							"popupText": "Test popup"
						},
						"quantity": "ABUNDANT",
						"quality": "EXCELLENT",
						"dateFound": "never",
						"notes": "test"
					}
				"""));
	}

	@Test
	void findAllForageMapItems_returnEmptyList_onEmpty_DB() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/forageMapItems"))
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}

	@Test
	void findAllForageMapItems_returnsForageMapItems_onSuccess() throws Exception {
		forageWikiItemRepository.save(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("url-to-apple-tree-image")
				)
		);
		customMarkerRepository.save(
				new CustomMarker(
						"1",
						new double[] {51.0, 10.0},
						"test-icon",
						new int[] {64, 64},
						new int[] {32, 64},
						new int[] {0, 64},
						"Test popup"
				)
		);
		forageMapItemRepository.save(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("url-to-apple-tree-image")
						),
						new CustomMarker(
								"1",
								new double[] {51.0, 10.0},
								"test-icon",
								new int[] {64, 64},
								new int[] {32, 64},
								new int[] {0, 64},
								"Test popup"
						),
						ForageQuantity.ABUNDANT,
						ForageQuality.EXCELLENT,
						"never",
						"test"
				)
		);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/forageMapItems"))
				.andExpect(status().isOk())
				.andExpect(content().json("""
					[
						{
							"forageWikiItem": {
								"id": "1",
								"name": "Apple Tree",
								"category": "FRUIT",
								"source": "TREE",
								"description": "Apple Tree",
								"season": "FALL",
								"imageURLs": [
								  "url-to-apple-tree-image"
								]
							},
							"customMarker": {
								"id": "1",
								"position": [
									51.0,
									10.0
								],
								"iconUrl": "test-icon",
								"iconSize": [
									64, 64
								],
								"iconAnchor": [
									32, 64
								],
								"popupAnchor": [
									0, 64
								],
								"popupText": "Test popup"
							},
							"quantity": "ABUNDANT",
							"quality": "EXCELLENT",
							"dateFound": "never",
							"notes": "test"
						}
					]
				"""));
	}

	@Test
	void findAllForageMapItems_returnsForageMapItems_withoutInvalidCustomMarker() throws Exception {
		forageWikiItemRepository.save(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("url-to-apple-tree-image")
				)
		);
		forageMapItemRepository.save(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("url-to-apple-tree-image")
						),
						new CustomMarker(
								"1",
								new double[] {51.0, 10.0},
								"test-icon",
								new int[] {64, 64},
								new int[] {32, 64},
								new int[] {0, 64},
								"Test popup"
						),
						ForageQuantity.ABUNDANT,
						ForageQuality.EXCELLENT,
						"never",
						"test"
				)
		);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/forageMapItems"))
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));

	}

	@Test
	void findAllForageMapItems_returnsForageMapItems_withoutInvalidForageWikiItem() throws Exception {
		customMarkerRepository.save(
				new CustomMarker(
						"1",
						new double[] {51.0, 10.0},
						"test-icon",
						new int[] {64, 64},
						new int[] {32, 64},
						new int[] {0, 64},
						"Test popup"
				)
		);
		forageMapItemRepository.save(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("url-to-apple-tree-image")
						),
						new CustomMarker(
								"1",
								new double[] {51.0, 10.0},
								"test-icon",
								new int[] {64, 64},
								new int[] {32, 64},
								new int[] {0, 64},
								"Test popup"
						),
						ForageQuantity.ABUNDANT,
						ForageQuality.EXCELLENT,
						"never",
						"test"
				)
		);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/forageMapItems"))
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}

	@Test
	void findForageMapItemById_returnForageMapItem_onSuccess() throws Exception {
		forageWikiItemRepository.save(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("url-to-apple-tree-image")
				)
		);
		customMarkerRepository.save(
				new CustomMarker(
						"1",
						new double[] {51.0, 10.0},
						"test-icon",
						new int[] {64, 64},
						new int[] {32, 64},
						new int[] {0, 64},
						"Test popup"
				)
		);
		forageMapItemRepository.save(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("url-to-apple-tree-image")
						),
						new CustomMarker(
								"1",
								new double[] {51.0, 10.0},
								"test-icon",
								new int[] {64, 64},
								new int[] {32, 64},
								new int[] {0, 64},
								"Test popup"
						),
						ForageQuantity.ABUNDANT,
						ForageQuality.EXCELLENT,
						"never",
						"test"
				)
		);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/forageMapItems/1"))
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"forageWikiItem": {
							"id": "1",
							"name": "Apple Tree",
							"category": "FRUIT",
							"source": "TREE",
							"description": "Apple Tree",
							"season": "FALL",
							"imageURLs": [
							  "url-to-apple-tree-image"
							]
						},
						"customMarker": {
							"id": "1",
							"position": [
								51.0,
								10.0
							],
							"iconUrl": "test-icon",
							"iconSize": [
								64, 64
							],
							"iconAnchor": [
								32, 64
							],
							"popupAnchor": [
								0, 64
							],
							"popupText": "Test popup"
						},
						"quantity": "ABUNDANT",
						"quality": "EXCELLENT",
						"dateFound": "never",
						"notes": "test"
					}
				"""));
	}
}