package com.phaete.backend.forage.controller;

import com.phaete.backend.forage.model.*;
import com.phaete.backend.forage.repository.CustomMarkerRepository;
import com.phaete.backend.forage.repository.ForageMapItemRepository;
import com.phaete.backend.forage.repository.ForageWikiItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ForageMapItemControllerIntegrationTest extends AbstractMongoDBTestcontainer {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CustomMarkerRepository customMarkerRepository;

	@Autowired
	private ForageWikiItemRepository forageWikiItemRepository;

	@Autowired
	private ForageMapItemRepository forageMapItemRepository;

	@AfterEach
	void tearDown() {
		customMarkerRepository.deleteAll();
		forageWikiItemRepository.deleteAll();
		forageMapItemRepository.deleteAll();
	}

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
							]
						},
						"position": {
							"latitude": 0.0,
							"longitude": 0.0
						},
						"assessment": {
							"quality": "EXCELLENT",
							"quantity": "ABUNDANT"
						},
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
							]
						},
						"position": {
							"latitude": 0.0,
							"longitude": 0.0
						},
						"assessment": {
							"quality": "EXCELLENT",
							"quantity": "ABUNDANT"
						},
						"notes": "test"
					}
				"""));
	}

	@Test
	void findAllForageMapItems_returnEmptyList_onEmpty_DB() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/forageMapItems"))
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"true": [],
						"false" : []
					}
				"""));
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
						"test",
						"test-icon",
						new int[] {64, 64},
						new int[] {32, 64},
						new int[] {0, 64}
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
								"test",
								"test-icon",
								new int[] {64, 64},
								new int[] {32, 64},
								new int[] {0, 64}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"test"
				)
		);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/forageMapItems"))
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"true":
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
										"name": "test",
										"iconUrl": "test-icon",
										"iconSize": [
											64, 64
										],
										"iconAnchor": [
											32, 64
										],
										"popupAnchor": [
											0, 64
										]
									},
									"position": {
										"latitude": 0.0,
										"longitude": 0.0
									},
									"assessment": {
										"quality": "EXCELLENT",
										"quantity": "ABUNDANT"
									},
									"notes": "test"
								}
							],
						"false" : []
					}
				"""));
	}

	@Test
	void findAllForageMapItems_filtersOutForageMapItems_withInvalidCustomMarker() throws Exception {
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
								"test",
								"test-icon",
								new int[] {64, 64},
								new int[] {32, 64},
								new int[] {0, 64}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"test"
				)
		);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/forageMapItems"))
				.andExpect(status().isOk())
				.andExpect(content().json("""
						{
								"true": [],
								"false": [
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
												"customMarker": null,
												"position": {
													"latitude": 0.0,
													"longitude": 0.0
												},
												"assessment": {
													"quality": "EXCELLENT",
													"quantity": "ABUNDANT"
												},
												"notes": "test"
										}
				]
						}
				"""));

	}

	@Test
	void findAllForageMapItems_filtersOutForageMapItems_withInvalidForageWikiItem() throws Exception {
		customMarkerRepository.save(
				new CustomMarker(
						"1",
						"test",
						"test-icon",
						new int[] {64, 64},
						new int[] {32, 64},
						new int[] {0, 64}
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
								"test",
								"test-icon",
								new int[] {64, 64},
								new int[] {32, 64},
								new int[] {0, 64}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"test"
				)
		);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/forageMapItems"))
				.andExpect(status().isOk())
				.andExpect(content().json("""
						{
								"true": [],
								"false": [
										{
												"forageWikiItem": null,
												"customMarker": {
													"id": "1",
													"name":"test",
													"iconUrl": "test-icon",
													"iconSize": [
														64, 64
													],
													"iconAnchor": [
														32, 64
													],
													"popupAnchor": [
														0, 64
													]
												},
												"position": {
													"latitude": 0.0,
													"longitude": 0.0
												},
												"assessment": {
													"quality": "EXCELLENT",
													"quantity": "ABUNDANT"
												},
												"notes": "test"
										}
				]
						}
				"""));
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
						"test",
						"test-icon",
						new int[] {64, 64},
						new int[] {32, 64},
						new int[] {0, 64}
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
								"test",
								"test-icon",
								new int[] {64, 64},
								new int[] {32, 64},
								new int[] {0, 64}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
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
							"name": "test",
							"iconUrl": "test-icon",
							"iconSize": [
								64, 64
							],
							"iconAnchor": [
								32, 64
							],
							"popupAnchor": [
								0, 64
							]
						},
						"position": {
							"latitude": 0.0,
							"longitude": 0.0
						},
						"assessment": {
							"quality": "EXCELLENT",
							"quantity": "ABUNDANT"
						},
						"notes": "test"
					}
				"""));
	}

	@Test
	void findForageMapItemById_returnsNotFound_onEmptyDB() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/forageMapItems/1"))
				.andExpect(status().isNotFound());
	}

	@Test
	void updateForageMapItem_expectUpdated() throws Exception {
		forageWikiItemRepository.save(
				new ForageWikiItem(
						"1",
						"Apple",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Fruit of an Apple Tree",
						ForageSeason.FALL,
						List.of("url-to-apple-image")
				)
		);
		customMarkerRepository.save(
				new CustomMarker(
						"1",
						"test",
						"test-icon",
						new int[] {64, 64},
						new int[] {32, 64},
						new int[] {0, 64}
				)
		);
		forageMapItemRepository.save(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Fruit of an Apple Tree",
								ForageSeason.FALL,
								List.of("url-to-apple-image")
						),
						new CustomMarker(
								"1",
								"test",
								"test-icon",
								new int[] {64, 64},
								new int[] {32, 64},
								new int[] {0, 64}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"test"
				)
		);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/forageMapItems/1")
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
						"name": "test",
						"iconUrl": "test-icon",
						"iconSize": [
							64, 64
						],
						"iconAnchor": [
							32, 64
						],
						"popupAnchor": [
							0, 64
						]
					},
					"position": {
						"latitude": 0.0,
						"longitude": 0.0
					},
					"assessment": {
						"quality": "EXCELLENT",
						"quantity": "ABUNDANT"
					},
					"notes": "test"
				}
				"""))
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
							"name": "test",
							"iconUrl": "test-icon",
							"iconSize": [
								64, 64
							],
							"iconAnchor": [
								32, 64
							],
							"popupAnchor": [
								0, 64
							]
						},
						"position": {
							"latitude": 0.0,
							"longitude": 0.0
						},
						"assessment": {
							"quality": "EXCELLENT",
							"quantity": "ABUNDANT"
						},
						notes: "test"
					}
				"""));
	}

	@Test
	void updateForageMapItem_expectNotFound_onItemNotInDB() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/forageMapItems/1")
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
						"name": "test",
						"iconUrl": "test-icon",
						"iconSize": [
							64, 64
						],
						"iconAnchor": [
							32, 64
						],
						"popupAnchor": [
							0, 64
						]
					},
					"position": {
						"latitude": 0.0,
						"longitude": 0.0
					},
					"assessment": {
						"quality": "EXCELLENT",
						"quantity": "ABUNDANT"
					},
					"notes": "test"
				}
				"""))
				.andExpect(status().isNotFound());
	}

	@Test
	void deleteForageMapItem_expectPosition_onSuccess() throws Exception {
		forageWikiItemRepository.save(
				new ForageWikiItem(
						"1",
						"Apple",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Fruit of an Apple Tree",
						ForageSeason.FALL,
						List.of("url-to-apple-image")
				)
		);
		customMarkerRepository.save(
				new CustomMarker(
						"1",
						"test",
						"test-icon",
						new int[] {64, 64},
						new int[] {32, 64},
						new int[] {0, 64}
				)
		);
		forageMapItemRepository.save(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Fruit of an Apple Tree",
								ForageSeason.FALL,
								List.of("url-to-apple-image")
						),
						new CustomMarker(
								"1",
								"test",
								"test-icon",
								new int[] {64, 64},
								new int[] {32, 64},
								new int[] {0, 64}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"test"
				)
		);

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/forageMapItems/1"))
				.andExpect(status().isOk())
				.andExpect(content().string("0.0, 0.0"));
	}

	@Test
	void deleteForageMapItem_expectNotFound_onItemNotInDB() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/forageMapItems/1"))
				.andExpect(status().isNotFound());
	}
}