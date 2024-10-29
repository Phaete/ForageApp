package com.phaete.backend.forage.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public class AbstractMongoDBTestcontainer {

	public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.0")
			.withExposedPorts(27017);

	@DynamicPropertySource
	static void containersProperties(DynamicPropertyRegistry registry) {
		mongoDBContainer.start();
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}
}
