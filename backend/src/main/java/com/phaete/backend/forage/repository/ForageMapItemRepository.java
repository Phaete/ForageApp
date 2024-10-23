package com.phaete.backend.forage.repository;

import com.phaete.backend.forage.model.ForageMapItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForageMapItemRepository extends MongoRepository<ForageMapItem, String> {
}
