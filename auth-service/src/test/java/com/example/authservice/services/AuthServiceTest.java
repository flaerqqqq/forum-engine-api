package com.example.authservice.services;

import com.example.authservice.clients.UserClient;
import com.example.authservice.clients.dtos.UserServiceCreateRequestDto;
import com.example.authservice.clients.dtos.UserServiceResponseDto;
import com.example.authservice.dtos.UserRegisterRequestDto;
import com.example.authservice.dtos.UserRegisterResponseDto;
import com.example.authservice.entities.Role;
import com.example.authservice.entities.UserRole;
import com.example.authservice.repositories.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceTest {

    @MockBean
    UserRoleRepository userRoleRepository;

    @MockBean
    UserClient userClient;

    @Autowired
    AuthService authService;

    private LocalDateTime createdAt = LocalDateTime.now();
    private String id = "uuid";
    private Long roleId = 1L;

    private UserServiceCreateRequestDto userCreateRequest;
    private UserServiceResponseDto userCreateResponse;
    private UserRole userRole;
    private UserRegisterRequestDto registerRequest;
    private UserRegisterResponseDto registerResponse;

    @BeforeEach
    void setUp() {

        userCreateRequest = UserServiceCreateRequestDto.builder()
                .username("username1")
                .email("test1@example.com")
                .password("Password1234!")
                .build();
        userCreateResponse = UserServiceResponseDto.builder()
                .id(id)
                .username("username1")
                .email("test1@example.com")
                .createdAt(createdAt)
                .build();
        userRole = UserRole.builder()
                .userId(id)
                .role(new Role())
                .build();
        registerRequest = UserRegisterRequestDto.builder()
                .username("username1")
                .email("test1@example.com")
                .password("Password1234!")
                .build();
        registerResponse = UserRegisterResponseDto.builder()
                .id(id)
                .username("username1")
                .email("test1@example.com")
                .createdAt(createdAt)
                .build();
    }

    @AfterEach
    void cleanUp() {
        userCreateRequest = null;
        userCreateResponse = null;
        userRole = null;
        registerRequest = null;
        registerResponse = null;
    }

}
