package com.example.authservice.repositories;

import com.example.authservice.config.PostgreSQLTestContainerInitializer;
import com.example.authservice.entities.AuthUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DirtiesContext
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthUserRepositoryTest extends PostgreSQLTestContainerInitializer {

    @Autowired
    AuthUserRepository authUserRepository;

    private AuthUser authUser;

    @BeforeEach
    void initialize() {
        authUserRepository.deleteAll();
        authUser = AuthUser.builder()
                .id("someId")
                .username("username")
                .password("password")
                .build();
        authUserRepository.save(authUser);
    }

    @AfterEach
    void cleanup() {
        authUserRepository.deleteAll();
    }

    @Test
    void findByUsername_shouldReturnUser() {
        Optional<AuthUser> user = authUserRepository.findByUsername(authUser.getUsername());

        assertThat(user).isPresent();
        assertThat(user.get().getId()).isEqualTo(authUser.getId());
    }

    @Test
    void findByUsername_shouldReturnEmptyOptional_ifUserNotFound() {
        Optional<AuthUser> user = authUserRepository.findByUsername("any");

        assertThat(user).isNotPresent();
    }
}
