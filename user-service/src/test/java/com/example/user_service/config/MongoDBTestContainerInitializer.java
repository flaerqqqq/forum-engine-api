package com.example.user_service.config;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

public class MongoDBTestContainerInitializer {

    @Container
    public static MongoDBContainer mongoContainer = new MongoDBContainer("mongo");

    static {
        mongoContainer.start();
        var mappedPort = mongoContainer.getMappedPort(27017);
        System.setProperty("mongodb.container.port", mappedPort.toString());
    }
}
