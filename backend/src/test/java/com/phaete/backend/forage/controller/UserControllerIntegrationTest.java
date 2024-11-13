package com.phaete.backend.forage.controller;

import com.phaete.backend.forage.model.Role;
import com.phaete.backend.forage.model.User;
import com.phaete.backend.forage.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest extends AbstractMongoDBTestcontainer {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	void createUser_expectUserDTO() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						"origin": "github:123456",
						"name": "test",
						"email": "test@user.com",
						"imageUrl": "test",
						"role": "USER"
						}
				""")
		)
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"origin": "github:123456",
						"name": "test",
						"email": "test@user.com",
						"imageUrl": "test",
						"role": "USER"
					}
"""));
	}

	@Test
	void findAll_returnsEmptyList_onEmptyDB() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user"))
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}

	@Test
	void findAll_returnsList_onSuccess() throws Exception {
		userRepository.save(
				new User(
						"1",
						"origin",
						"name",
						"email",
						"imageUrl",
						Role.USER
				)
		);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user"))
				.andExpect(status().isOk())
				.andExpect(content().json("""
					[
						{
						"origin": "origin",
						"name": "name",
						"email": "email",
						"imageUrl": "imageUrl",
						"role": "USER"
						}
					]
				"""));
	}



	@Test
	void findMe_returnsGithubUserDTO_onSuccess() throws Exception {
		userRepository.save(
				new User(
						"1",
						"github:user",
						"name",
						"email",
						"imageUrl",
						Role.USER
				)
		);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/user/me")
						.with(oidcLogin().userInfoToken(
								token -> token
										.claim("id", "user")
										.claim("sub", "user")
										.claim("name", "name")
										.claim("email", "email")
										.claim("avatar_url", "imageUrl")
						))
				)
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"origin": "github:user",
						"name": "name",
						"email": "email",
						"imageUrl": "imageUrl",
						"role": "USER"
					}
				"""));
	}

	@Test
	void findMe_returnsGoogleUserDTO_onSuccess() throws Exception {
		userRepository.save(
				new User(
						"2",
						"google:user",
						"name",
						"email",
						"imageUrl",
						Role.USER
				)
		);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/user/me")
						.with(oidcLogin().userInfoToken(
								token -> token
										.claim("id", "user")
										.claim("sub", "user")
										.claim("name", "name")
										.claim("email", "email")
										.claim("picture", "imageUrl")
						))
				)
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"origin": "google:user",
						"name": "name",
						"email": "email",
						"imageUrl": "imageUrl",
						"role": "USER"
					}
				"""));
	}

	@Test
	void findMe_throwsUserNotFoundException() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/user/me")
						.with(oidcLogin().userInfoToken(
								token -> token
										.claim("id", "user")
										.claim("sub", "user")
										.claim("name", "name")
										.claim("email", "email")
										.claim("avatar_url", "imageUrl")
						))
		)
				.andExpect(status().isNotFound());
	}

	@Test
	void findMe_throwsInvalidAuthenticationException() throws Exception {
		mockMvc.perform(
						MockMvcRequestBuilders.get("/api/user/me")
								.with(oidcLogin().userInfoToken(
										token -> token
												.claim("id", "user")
												.claim("sub", "user")
												.claim("name", "name")
												.claim("email", "email")
												.claim("image", "imageUrl")
								))
				)
				.andExpect(status().isUnauthorized());
	}

	@Test
	void updateUser_returnsUserDTO_onSuccess() throws Exception {
		userRepository.save(
				new User(
						"2",
						"github:user",
						"name",
						"email",
						"imageUrl",
						Role.USER
				)
		);

		mockMvc.perform(
				MockMvcRequestBuilders.put("/api/user")
						.with(oidcLogin().userInfoToken(
								token -> token
										.claim("id", "user")
										.claim("sub", "user")
										.claim("name", "name")
										.claim("email", "email")
										.claim("avatar_url", "imageUrl")
						))
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"origin": "github:user",
									"name": "newName",
									"email": "newEmail",
									"imageUrl": "imageUrl",
									"role": "USER"
								}
						""")
		)
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"origin": "github:user",
						"name": "newName",
						"email": "newEmail",
						"imageUrl": "imageUrl",
						"role": "USER"
					}
				"""));
	}

	@Test
	void updateUser_throwsUserNotFoundException() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.put("/api/user")
						.with(oidcLogin().userInfoToken(
								token -> token
										.claim("id", "user")
										.claim("sub", "user")
										.claim("name", "name")
										.claim("email", "email")
										.claim("avatar_url", "imageUrl")
						))
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"origin": "github:user",
									"name": "newName",
									"email": "newEmail",
									"imageUrl": "imageUrl",
									"role": "USER"
								}
						""")
		)
				.andExpect(status().isNotFound());
	}

	@Test
	void updateUser_throwsInvalidAuthenticationException() throws Exception {
		userRepository.save(
				new User(
						"1",
						"github:user1",
						"name",
						"email",
						"imageUrl",
						Role.USER
				)
		);
		userRepository.save(
				new User(
						"2",
						"github:user2",
						"name",
						"email",
						"imageUrl",
						Role.MODERATOR
				)
		);

		mockMvc.perform(
						MockMvcRequestBuilders.put("/api/user")
								.with(oidcLogin().userInfoToken(
										token -> token
												.claim("id", "user1")
												.claim("sub", "user1")
												.claim("name", "name")
												.claim("email", "email")
												.claim("avatar_url", "imageUrl")
								))
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
								{
									"origin": "github:user2",
									"name": "newName",
									"email": "newEmail",
									"imageUrl": "imageUrl",
									"role": "MODERATOR"
								}
						""")
				)
				.andExpect(status().isUnauthorized());
	}
}