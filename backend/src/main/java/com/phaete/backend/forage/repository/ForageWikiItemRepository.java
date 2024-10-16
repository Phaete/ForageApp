package com.phaete.backend.forage.repository;

import com.phaete.backend.forage.model.ForageWikiItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface provides basic CRUD operations for the {@link ForageWikiItem} entity.
 * It extends the {@link MongoRepository} interface and thereby provides
 * methods for creating, reading, updating and deleting custom markers.
 * <p>
 * The interface is annotated with the {@link Repository} annotation to mark it as a Spring Data
 * repository. This allows Spring Data to automatically generate implementations for the
 * methods declared in this interface.
 */
@Repository
public interface ForageWikiItemRepository extends MongoRepository<ForageWikiItem, String> {
}
