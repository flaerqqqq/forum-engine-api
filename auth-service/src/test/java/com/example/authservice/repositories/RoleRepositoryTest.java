package com.example.authservice.repositories;

import com.example.authservice.config.PostgreSQLTestContainerInitializer;
import com.example.authservice.entities.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DirtiesContext
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTest extends PostgreSQLTestContainerInitializer {

    @Autowired
    RoleRepository roleRepository;

    Role role;

    @BeforeEach
    public void setup() {
        roleRepository.deleteAll();
        roleRepository.findAll();
        role = Role.builder()
                .name(Role.RoleName.ROLE_USER)
                .build();
        roleRepository.save(role);
    }

    @Test
    void findByName_shouldReturn_ifNameIsCorrect() {
        Role actualRole = roleRepository.findByName(Role.RoleName.ROLE_USER).get();

        assertThat(actualRole.getName()).isEqualTo(role.getName());
    }
}