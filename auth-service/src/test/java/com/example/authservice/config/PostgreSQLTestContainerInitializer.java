package com.example.authservice.config;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class PostgreSQLTestContainerInitializer {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    static {
        postgreSQLContainer.start();
        var port = postgreSQLContainer.getMappedPort(5342);
        System.setProperty("postgresql.container.port", port.toString());
    }
}
