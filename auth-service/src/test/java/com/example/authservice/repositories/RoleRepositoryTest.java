package com.example.authservice.repositories;

import com.example.authservice.config.PostgreSQLTestContainerInitializer;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@ActiveProfiles("auth-test-containers")
public class RoleRepositoryTest extends PostgreSQLTestContainerInitializer {
}
