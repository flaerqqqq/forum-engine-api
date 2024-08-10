package com.example.authservice.repositories;

import com.example.authservice.entities.AuthUser;
import com.example.authservice.entities.Role;
import com.example.authservice.entities.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DirtiesContext
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRoleRepositoryTest {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    RoleRepository roleRepository;

    private UserRole userRole1;
    private AuthUser authUser;
    private Role role;

    @BeforeEach
    void initialize() {
        role = Role.builder()
                .name(Role.RoleName.ROLE_USER)
                .build();
        authUser = AuthUser.builder()
                .id("someId")
                .username("username")
                .password("password")
                .build();
        userRole1 = UserRole.builder()
                .role(role)
                .authUser(authUser)
                .build();
        roleRepository.save(role);
        authUserRepository.save(authUser);
        userRoleRepository.save(userRole1);
    }

    @AfterEach
    void cleanup() {
        userRoleRepository.deleteAll();
        roleRepository.deleteAll();
        authUserRepository.deleteAll();
    }

    @Test
    void findAllByUserId_shouldReturnListOfUserRoles() {
        List<UserRole> userRoles = userRoleRepository.findAllByUserId(authUser.getId());

        assertThat(userRoles.size()).isEqualTo(1);
        assertThat(userRoles.get(0).getId()).isEqualTo(userRole1.getId());
    }

    @Test
    void findAllByUserId_shouldReturnEmptyList_ifUserDoNotHaveRoles() {
        List<UserRole> userRoles = userRoleRepository.findAllByUserId("any");

        assertThat(userRoles.size()).isEqualTo(0);
    }
}
