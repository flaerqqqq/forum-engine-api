package com.example.authservice.repositories;

import com.example.authservice.config.PostgreSQLTestContainerInitializer;
import com.example.authservice.entities.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTest extends PostgreSQLTestContainerInitializer {

    @Autowired
    RoleRepository roleRepository;

    Role role = Role.builder()
            .id(123L)
            .name(Role.RoleName.ROLE_USER)
            .build();

    @BeforeEach
    public void setup() {
        roleRepository.deleteAll();
        roleRepository.save(role);
    }

    @Test
    void findByName_shouldReturn_ifNameIsCorrect() {
        Role actualRole = roleRepository.findByName(Role.RoleName.ROLE_USER).get();

        assertThat(actualRole.getName()).isEqualTo(role.getName());
    }
}
