package com.phaete.backend.forage.service;

import com.phaete.backend.forage.model.*;
import com.phaete.backend.forage.repository.ForageMapItemRepository;
import com.phaete.backend.forage.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ForageMapItemServiceTest {

	private final ForageMapItemRepository forageMapItemRepository = mock(ForageMapItemRepository.class);
	private final IdService idService = mock(IdService.class);
	private final UserRepository userRepository = mock(UserRepository.class);
	private final OAuth2AuthenticationToken oAuth2AuthenticationToken = mock(OAuth2AuthenticationToken.class);

	private final ConverterService converterService = new ConverterService(idService, userRepository);

	ForageMapItemService forageMapItemService = new ForageMapItemService(
			forageMapItemRepository,
			converterService,
			new UserService(userRepository, converterService)
	);

	@Test
	void createForageMapItem_expectDTO_onSuccess() {
		ForageMapItemDTO expectedForageMapItemDTO = new ForageMapItemDTO(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("test")
				),
				new CustomMarker(
						"1",
						"test",
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0}
				),
				new GeoPosition(0.0, 0.0),
				new ForageMapItemOwnership("test", true),
				new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
				"notes");

		when(forageMapItemRepository.save(any(ForageMapItem.class))).thenReturn(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						),
						new CustomMarker(
								"1",
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemOwnership("test", true),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes")
		);
		when(idService.generateId()).thenReturn("1");



		ForageMapItemDTO actualForageMapItemDTO = forageMapItemService.createForageMapItem(expectedForageMapItemDTO);
		verify(forageMapItemRepository).save(any(ForageMapItem.class));
		assertEquals(expectedForageMapItemDTO, actualForageMapItemDTO);
	}

	@Test
	void findAllForageMapItems_expectListWithPublicForageMapItems_onSuccess() throws UserNotFoundException, InvalidAuthenticationException {
		List<ForageMapItem> expectedForageMapItems = List.of(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						),
						new CustomMarker(
								"1",
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemOwnership("test", true),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes")
		);
		when(forageMapItemRepository.findAll()).thenReturn(expectedForageMapItems);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAll(oAuth2AuthenticationToken).get(true);
		verify(forageMapItemRepository).findAll();
		assertEquals(expectedForageMapItems, actualForageMapItems);
	}

	@Test
	void findAllForageMapItems_expectListWithPublicForageMapItems_onAnonymousSuccess() throws UserNotFoundException, InvalidAuthenticationException {
		List<ForageMapItem> expectedForageMapItems = List.of(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						),
						new CustomMarker(
								"1",
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemOwnership("test", true),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes")
		);
		when(forageMapItemRepository.findAll()).thenReturn(expectedForageMapItems);


		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAll(null).get(true);
		verify(forageMapItemRepository).findAll();
		assertEquals(expectedForageMapItems, actualForageMapItems);
	}

	@Test
	void findAllForageMapItems_expectListWithoutNonPublicMarkers_onSuccess() throws UserNotFoundException, InvalidAuthenticationException {
		List<ForageMapItem> forageMapItems = List.of(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						),
						new CustomMarker(
								"1",
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemOwnership("test", false),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes")
		);
		when(forageMapItemRepository.findAll()).thenReturn(forageMapItems);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAll(oAuth2AuthenticationToken).get(true);
		verify(forageMapItemRepository).findAll();
		assertEquals(List.of(), actualForageMapItems);
	}

	@Test
	void findAllForageMapItems_expectListWithPrivateMarkers_onSuccess() throws UserNotFoundException, InvalidAuthenticationException {
		List<ForageMapItem> expectedForageMapItems = List.of(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						),
						new CustomMarker(
								"1",
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemOwnership("github:1234", false),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes")
		);
		when(forageMapItemRepository.findAll()).thenReturn(expectedForageMapItems);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAll(oAuth2AuthenticationToken).get(true);
		verify(forageMapItemRepository).findAll();
		assertEquals(expectedForageMapItems, actualForageMapItems);
	}

	@Test
	void findAllForageMapItems_expectListWithAllForageMapItems_onAdminSuccess() throws UserNotFoundException, InvalidAuthenticationException {
		List<ForageMapItem> expectedForageMapItems = List.of(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						),
						new CustomMarker(
								"1",
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemOwnership("test", false),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes")
		);
		when(forageMapItemRepository.findAll()).thenReturn(expectedForageMapItems);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.ADMIN
				)
		));

		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAll(oAuth2AuthenticationToken).get(true);
		verify(forageMapItemRepository).findAll();
		assertEquals(expectedForageMapItems, actualForageMapItems);
	}

	@Test
	void findAllForageMapItems_expectList_excludeItemsWithoutWikiItemInDB() throws UserNotFoundException, InvalidAuthenticationException {
		when(forageMapItemRepository.findAll()).thenReturn(
				List.of(
						new ForageMapItem(
								"1",
								null,
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(0.0, 0.0),
								new ForageMapItemOwnership("test", true),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAll(oAuth2AuthenticationToken).get(true);
		verify(forageMapItemRepository).findAll();
		assertEquals(List.of(), actualForageMapItems);
	}

	@Test
	void findAllForageMapItems_expectList_excludeItemsWithoutCustomMarkerInDB() throws UserNotFoundException, InvalidAuthenticationException {
		when(forageMapItemRepository.findAll()).thenReturn(
				List.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								null,
								new GeoPosition(0.0, 0.0),
								new ForageMapItemOwnership("test", true),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAll(oAuth2AuthenticationToken).get(true);
		verify(forageMapItemRepository).findAll();
		assertEquals(List.of(), actualForageMapItems);
	}

	@Test
	void findAllForageMapItems_expectList_excludeItemsWithoutWikiItemInDB_onAdminSuccess() throws UserNotFoundException, InvalidAuthenticationException {
		when(forageMapItemRepository.findAll()).thenReturn(
				List.of(
						new ForageMapItem(
								"1",
								null,
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(0.0, 0.0),
								new ForageMapItemOwnership("test", true),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.ADMIN
				)
		));

		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAll(oAuth2AuthenticationToken).get(true);
		verify(forageMapItemRepository).findAll();
		assertEquals(List.of(), actualForageMapItems);
	}

	@Test
	void findAllForageMapItems_expectList_excludeItemsWithoutCustomMarkerInDB_onAdminSuccess() throws UserNotFoundException, InvalidAuthenticationException {
		when(forageMapItemRepository.findAll()).thenReturn(
				List.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								null,
								new GeoPosition(0.0, 0.0),
								new ForageMapItemOwnership("test", false),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.ADMIN
				)
		));

		List<ForageMapItem> actualForageMapItems = forageMapItemService.findAll(oAuth2AuthenticationToken).get(true);
		verify(forageMapItemRepository).findAll();
		assertEquals(List.of(), actualForageMapItems);
	}

	@Test
	void findForageMapItemById_expectPrivateForageMapItemDTO_onUserSuccess()
			throws ForageMapItemNotFoundException, MarkerNotFoundException, ForageWikiItemNotFoundException, UserNotFoundException, InvalidAuthenticationException {
		ForageMapItemDTO expectedForageMapItemDTO = new ForageMapItemDTO(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("test")
				),
				new CustomMarker(
						"1",
						"test",
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0}
				),
				new GeoPosition(0.0, 0.0),
				new ForageMapItemOwnership("github:1234", false),
				new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
				"notes");

		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(0.0, 0.0),
								new ForageMapItemOwnership("github:1234", false),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		assertEquals(expectedForageMapItemDTO, forageMapItemService.findForageMapItemById("1", oAuth2AuthenticationToken));
	}

	@Test
	void findForageMapItemById_expectPublicForageMapItemDTO_onAnonymousSuccess()
			throws ForageMapItemNotFoundException, MarkerNotFoundException, ForageWikiItemNotFoundException, UserNotFoundException, InvalidAuthenticationException {
		ForageMapItemDTO expectedForageMapItemDTO = new ForageMapItemDTO(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("test")
				),
				new CustomMarker(
						"1",
						"test",
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0}
				),
				new GeoPosition(0.0, 0.0),
				new ForageMapItemOwnership("github:1234", true),
				new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
				"notes");

		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(0.0, 0.0),
								new ForageMapItemOwnership("github:1234", true),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);

		assertEquals(expectedForageMapItemDTO, forageMapItemService.findForageMapItemById("1", null));
	}

	@Test
	void findForageMapItemById_expectPrivateForageMapItemDTO_onAdminSuccess()
			throws ForageMapItemNotFoundException, MarkerNotFoundException, ForageWikiItemNotFoundException, UserNotFoundException, InvalidAuthenticationException {
		ForageMapItemDTO expectedForageMapItemDTO = new ForageMapItemDTO(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("test")
				),
				new CustomMarker(
						"1",
						"test",
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0}
				),
				new GeoPosition(0.0, 0.0),
				new ForageMapItemOwnership("github:1234", false),
				new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
				"notes");

		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(0.0, 0.0),
								new ForageMapItemOwnership("github:1234", false),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "123"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:123",
						"test123",
						"test123",
						"test123",
						Role.ADMIN
				)
		));

		assertEquals(expectedForageMapItemDTO, forageMapItemService.findForageMapItemById("1", oAuth2AuthenticationToken));
	}

	@Test
	void findForageMapItemById_expectThrows_onWikiItemNotFound() {
		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								null,
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(0.0, 0.0),
								new ForageMapItemOwnership("github:1234", false),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		assertThrows(ForageWikiItemNotFoundException.class, () -> forageMapItemService.findForageMapItemById("1", oAuth2AuthenticationToken));
	}

	@Test
	void findForageMapItemById_expectThrows_onMarkerNotFound() {
		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								null,
								new GeoPosition(0.0, 0.0),
								new ForageMapItemOwnership("github:1234", true),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		assertThrows(MarkerNotFoundException.class, () -> forageMapItemService.findForageMapItemById("1", oAuth2AuthenticationToken));
	}

	@Test
	void findForageMapItemById_expectThrows_onForageMapItemNotFound() {
		when(forageMapItemRepository.findById("1")).thenReturn(Optional.empty());
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		assertThrows(ForageMapItemNotFoundException.class, () -> forageMapItemService.findForageMapItemById("1", oAuth2AuthenticationToken));
	}

	@Test
	void findForageMapItemById_expectThrows_onInvalidAuthority() {
		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(0.0, 0.0),
								new ForageMapItemOwnership("github:1234", false),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "123"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:123",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		assertThrows(InvalidAuthenticationException.class, () -> forageMapItemService.findForageMapItemById("1", oAuth2AuthenticationToken));

	}

	@Test
	void updateForageMapItem_expectDTO_onUserOwnedSuccess() throws ForageMapItemNotFoundException, UserNotFoundException, InvalidAuthenticationException {
		ForageMapItemDTO expectedForageMapItemDTO = new ForageMapItemDTO(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("test")
				),
				new CustomMarker(
						"1",
						"test",
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0}
				),
				new GeoPosition(0.0, 0.0),
				new ForageMapItemOwnership("github:1234", true),
				new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
				"notes");

		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(1.0, 1.0),
								new ForageMapItemOwnership("github:1234", true),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		when(forageMapItemRepository.save(any(ForageMapItem.class))).thenReturn(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						),
						new CustomMarker(
								"1",
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemOwnership("github:1234", true),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes")
		);

		ForageMapItemDTO actualForageMapItem = forageMapItemService.updateForageMapItem("1", expectedForageMapItemDTO, oAuth2AuthenticationToken);
		verify(forageMapItemRepository).save(any(ForageMapItem.class));
		assertEquals(expectedForageMapItemDTO, actualForageMapItem);
	}

	@Test
	void updateForageMapItem_expectDTO_onAdminSuccess()
			throws ForageMapItemNotFoundException, UserNotFoundException, InvalidAuthenticationException {
		ForageMapItemDTO expectedForageMapItemDTO = new ForageMapItemDTO(
				new ForageWikiItem(
						"1",
						"Apple Tree",
						ForageCategory.FRUIT,
						ForageSource.TREE,
						"Apple Tree",
						ForageSeason.FALL,
						List.of("test")
				),
				new CustomMarker(
						"1",
						"test",
						"",
						new int[] {0, 0},
						new int[] {0, 0},
						new int[] {0, 0}
				),
				new GeoPosition(0.0, 0.0),
				new ForageMapItemOwnership("github:1234", true),
				new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
				"notes");

		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(1.0, 1.0),
								new ForageMapItemOwnership("github:1234", true),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "123"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:123",
						"test123",
						"test123",
						"test123",
						Role.ADMIN
				)
		));

		when(forageMapItemRepository.save(any(ForageMapItem.class))).thenReturn(
				new ForageMapItem(
						"1",
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						),
						new CustomMarker(
								"1",
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemOwnership("github:1234", true),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes")
		);

		ForageMapItemDTO actualForageMapItem = forageMapItemService.updateForageMapItem("1", expectedForageMapItemDTO, oAuth2AuthenticationToken);
		verify(forageMapItemRepository).save(any(ForageMapItem.class));
		assertEquals(expectedForageMapItemDTO, actualForageMapItem);
	}

	@Test
	void updateForageMapItem_expectThrows_onForageMapItemNotFound() {
		when(forageMapItemRepository.findById("1")).thenReturn(Optional.empty());
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		assertThrows(ForageMapItemNotFoundException.class, () -> forageMapItemService.updateForageMapItem(
				"1",
				new ForageMapItemDTO(
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						),
						new CustomMarker(
								"1",
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemOwnership("test", true),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes"
				),
				oAuth2AuthenticationToken
		));
	}

	@Test
	void updateForageMapItem_expectInvalidAuthenticationException_onPublicForageMapItem() {
		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								new CustomMarker(
										"1",
										"test",
										"",
										new int[]{0, 0},
										new int[]{0, 0},
										new int[]{0, 0}
								),
								new GeoPosition(1.0, 1.0),
								new ForageMapItemOwnership("github:1234", true),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "123"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:123",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		assertThrows(InvalidAuthenticationException.class, () -> forageMapItemService.updateForageMapItem(
				"1",
				new ForageMapItemDTO(
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						),
						new CustomMarker(
								"1",
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemOwnership("github:1234", true),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes"
				),
				oAuth2AuthenticationToken
		));
	}

	@Test
	void updateForageMapItem_expectInvalidAuthenticationException_onSubmittingDifferentUserOwnedForageMapItem() {
		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								new CustomMarker(
										"1",
										"test",
										"",
										new int[]{0, 0},
										new int[]{0, 0},
										new int[]{0, 0}
								),
								new GeoPosition(1.0, 1.0),
								new ForageMapItemOwnership("github:1234", true),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		assertThrows(InvalidAuthenticationException.class, () -> forageMapItemService.updateForageMapItem(
				"1",
				new ForageMapItemDTO(
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						),
						new CustomMarker(
								"1",
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemOwnership("github:123", true),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes"
				),
				oAuth2AuthenticationToken
		));
	}

	@Test
	void updateForageMapItem_expectInvalidAuthenticationException_onAnonymousUser() {
		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								new CustomMarker(
										"1",
										"test",
										"",
										new int[]{0, 0},
										new int[]{0, 0},
										new int[]{0, 0}
								),
								new GeoPosition(1.0, 1.0),
								new ForageMapItemOwnership("github:1234", true),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);


		assertThrows(InvalidAuthenticationException.class, () -> forageMapItemService.updateForageMapItem(
				"1",
				new ForageMapItemDTO(
						new ForageWikiItem(
								"1",
								"Apple Tree",
								ForageCategory.FRUIT,
								ForageSource.TREE,
								"Apple Tree",
								ForageSeason.FALL,
								List.of("test")
						),
						new CustomMarker(
								"1",
								"test",
								"",
								new int[] {0, 0},
								new int[] {0, 0},
								new int[] {0, 0}
						),
						new GeoPosition(0.0, 0.0),
						new ForageMapItemOwnership("github:123", true),
						new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
						"notes"
				),
				null
		));
	}

	@Test
	void deleteForageMapItem_expectPosition_onPrivateUserSuccess() throws ForageMapItemNotFoundException, UserNotFoundException, InvalidAuthenticationException {
		String expectedPosition = "1.0, 1.0";

		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(1.0, 1.0),
								new ForageMapItemOwnership("github:1234", false),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "1234"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:1234",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		String actualPosition = forageMapItemService.deleteForageMapItem("1", oAuth2AuthenticationToken);
		verify(forageMapItemRepository).deleteById("1");
		assertEquals(expectedPosition, actualPosition);
	}

	@Test
	void deleteForageMapItem_expectPosition_onAdminSuccess() throws ForageMapItemNotFoundException, UserNotFoundException, InvalidAuthenticationException {
		String expectedPosition = "1.0, 1.0";

		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(1.0, 1.0),
								new ForageMapItemOwnership("github:1234", false),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "123"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:123",
						"test123",
						"test123",
						"test123",
						Role.ADMIN
				)
		));

		String actualPosition = forageMapItemService.deleteForageMapItem("1", oAuth2AuthenticationToken);
		verify(forageMapItemRepository).deleteById("1");
		assertEquals(expectedPosition, actualPosition);
	}

	@Test
	void deleteForageMapItem_expectThrows_onPublicNotUserOwned() {
		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(1.0, 1.0),
								new ForageMapItemOwnership("github:1234", false),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);
		when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(new DefaultOAuth2User(null,
				Map.ofEntries(
						Map.entry("id", "123"),
						Map.entry("name", "name"),
						Map.entry("email", "email"),
						Map.entry("avatar_url", "imageUrl")
				),
				"name"));
		when(userRepository.findByOrigin(any(String.class))).thenReturn(Optional.of(
				new User(
						"1",
						"github:123",
						"test123",
						"test123",
						"test123",
						Role.USER
				)
		));

		assertThrows(InvalidAuthenticationException.class, () -> forageMapItemService.deleteForageMapItem("1", oAuth2AuthenticationToken));
	}

	@Test
	void deleteForageMapItem_expectThrows_onAnonymous() {
		when(forageMapItemRepository.findById("1")).thenReturn(
				Optional.of(
						new ForageMapItem(
								"1",
								new ForageWikiItem(
										"1",
										"Apple Tree",
										ForageCategory.FRUIT,
										ForageSource.TREE,
										"Apple Tree",
										ForageSeason.FALL,
										List.of("test")
								),
								new CustomMarker(
										"1",
										"test",
										"",
										new int[] {0, 0},
										new int[] {0, 0},
										new int[] {0, 0}
								),
								new GeoPosition(1.0, 1.0),
								new ForageMapItemOwnership("github:1234", false),
								new ForageMapItemAssessment(ForageQuality.EXCELLENT, ForageQuantity.ABUNDANT),
								"notes")
				)
		);

		assertThrows(InvalidAuthenticationException.class, () -> forageMapItemService.deleteForageMapItem("1", null));
	}

	@Test
	void deleteForageMapItem_expectStatusNotFound_onEmptyDB() {
		when(forageMapItemRepository.findById("1")).thenReturn(Optional.empty());

		assertThrows(ForageMapItemNotFoundException.class, () -> forageMapItemService.deleteForageMapItem("1", oAuth2AuthenticationToken));
	}
}