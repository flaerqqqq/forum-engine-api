package com.example.user_service.repositories;

import com.example.user_service.config.MongoDBTestContainerInitializer;
import com.example.user_service.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@DataMongoTest
@ActiveProfiles("test-containers")
public class UserRepositoryTest extends MongoDBTestContainerInitializer {

    @Autowired
    UserRepository userRepository;


    @BeforeEach
    void setUp() {
        User user1 = User.builder()
                .username("test1")
                .email("test1@example.com")
                .password("password1234")
                .build();
        User user2 = User.builder()
                .username("test2")
                .email("test2@example.com")
                .password("password1234")
                .build();
        userRepository.saveAll(List.of(user1, user2));
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void existsByEmail_shouldReturnTrue_ifUserActuallyExists() {
        boolean result = userRepository.existsByEmail("test1@example.com");

        assertThat(result).isTrue();
    }

    @Test
    void existsByEmail_shouldReturnFalse_ifUserDoesNotExist() {
        boolean result = userRepository.existsByEmail("someNotUsedEmail@example.com");

        assertThat(result).isFalse();
    }

    @Test
    void existsByUsername_shouldReturnTrue_ifUserActuallyExists() {
        boolean result = userRepository.existsByUsername("test1");

        assertThat(result).isTrue();
    }

    @Test
    void existsByUsername_shouldReturnFalse_ifUserDoesNotExist() {
        boolean result = userRepository.existsByUsername("someNotUsedUsername");

        assertThat(result).isFalse();
    }
}
