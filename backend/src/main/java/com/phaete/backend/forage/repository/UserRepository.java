package com.phaete.backend.forage.repository;

import com.phaete.backend.forage.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByOrigin(String origin);
}
