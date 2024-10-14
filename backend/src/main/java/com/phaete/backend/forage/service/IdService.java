package com.phaete.backend.forage.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service to generate unique IDs. This service is used to generate IDs for
 * all objects requiring one. The IDs are generated using the UUID
 * library and are therefore globally unique.
 *
 * @author -St4n aka Phaete
 */
@Service
public class IdService {

	/**
	 * Generates a globally unique ID.
	 *
	 * <p>
	 * The generated ID is a random UUID and is therefore globally unique.
	 * The UUID is generated using the {@link UUID#randomUUID()} method.
	 *
	 * @return a globally unique ID
	 */
	public String generateId() {
		return UUID.randomUUID().toString();
	}
}
