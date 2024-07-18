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
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    void init () {
        User user = User.builder()
                .username("test")
                .email("example@gmail.com")
                .password("123456")
                .build();

        userRepository.save(user);

        List<User> listOfUsers = userRepository.findAll();

        assertThat(listOfUsers.size()).isEqualTo(2);
    }

    @Test
    @Order(2)
    void test() {
        List<User> listOfUsers = userRepository.findAll();

        assertThat(listOfUsers.size()).isEqualTo(1);

    }



}
