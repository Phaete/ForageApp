package com.phaete.backend.forage.controller;

import com.phaete.backend.forage.model.ForageCategory;
import com.phaete.backend.forage.model.ForageSeason;
import com.phaete.backend.forage.model.ForageSource;
import com.phaete.backend.forage.model.ForageWikiItem;
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
class ForageWikiItemControllerIntegrationTest extends AbstractMongoDBTestcontainer {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ForageWikiItemRepository forageWikiItemRepository;

	@AfterEach
	void tearDown() {
		forageWikiItemRepository.deleteAll();
	}

	@Test
	void createForageWikiItem_expectCreated() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/forageWikiItems")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"name": "Apple Tree",
						"category": "FRUIT",
						"source": "TREE",
						"description": "Apple Tree",
						"season": "FALL",
						"imageURLs": ["test"]
					}
				"""))
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"name": "Apple Tree",
						"category": "FRUIT",
						"source": "TREE",
						"description": "Apple Tree",
						"season": "FALL",
						"imageURLs": ["test"]
					}
				"""));
	}

	@Test
	void findAllForageWikiItems_returnsEmptyList_onEmptyDB() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/forageWikiItems"))
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}

	@Test
	void findForageWikiItemById_expectStatusNotFound_onEmptyDB() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/forageWikiItems/1"))
				.andExpect(status().isNotFound());
	}

	@Test
	void findForageWikiItemById_expectForageWikiItem_onSuccess() throws Exception {
		forageWikiItemRepository.save(
				new ForageWikiItem("1", "Apple Tree", ForageCategory.FRUIT, ForageSource.TREE, "Apple Tree", ForageSeason.FALL, List.of("test"))
		);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/forageWikiItems/1"))
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"name": "Apple Tree",
						"category": "FRUIT",
						"source": "TREE",
						"description": "Apple Tree",
						"season": "FALL",
						"imageURLs": ["test"]
					}
				"""));
	}

	@Test
	void updateForageWikiItem_expectStatusNotFound_onEmptyDB() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/forageWikiItems/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"name": "Apple Tree",
						"category": "FRUIT",
						"source": "TREE",
						"description": "Apple Tree",
						"season": "FALL",
						"imageURLs": ["test"]
					}
				"""))
				.andExpect(status().isNotFound());
	}

	@Test
	void updateForageWikiItem_expectForageWikiItemDTO_onSuccess() throws Exception {
		forageWikiItemRepository.save(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("test")
				)
		);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/forageWikiItems/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"name": "Apple Tree",
						"category": "FRUIT",
						"source": "TREE",
						"description": "Apple Tree123",
						"season": "FALL",
						"imageURLs": ["different_test"]
					}
				"""))
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"name": "Apple Tree",
						"category": "FRUIT",
						"source": "TREE",
						"description": "Apple Tree123",
						"season": "FALL",
						"imageURLs": ["different_test"]
					}
				"""));
	}

	@Test
	void deleteForageWikiItem_expectStatusNotFound_onEmptyDB() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/forageWikiItems/1"))
				.andExpect(status().isNotFound());
	}

	@Test
	void deleteForageWikiItem_expectName_onSuccess() throws Exception {
		forageWikiItemRepository.save(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("test")
				)
		);

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/forageWikiItems/1"))
				.andExpect(status().isOk())
				.andExpect(content().string("Apple Tree"));
	}
}