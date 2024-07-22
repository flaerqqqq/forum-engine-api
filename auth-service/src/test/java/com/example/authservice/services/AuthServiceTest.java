package com.example.authservice.services;

import com.example.authservice.dtos.UserRegisterRequestDto;
import com.example.authservice.dtos.UserRegisterResponseDto;
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
    UserServiceClient userServiceClient;

    @Autowired
    AuthService authService;

    private LocalDateTime createdAt = LocalDateTime.now();
    private String id = "uuid";
    private Long roleId = 1L;

    private UserCreateRequestDto userCreateRequest;
    private UserCreateResponseDto userCreateResponse;
    private UserRole userRole;
    private UserRegisterRequestDto registerRequest;
    private UserRegisterResponseDto registerResponse;

    @BeforeEach
    void setUp() {

        userCreateRequest = UserCreateRequestDto.builder()
                .username("username1")
                .email("test1@example.com")
                .password("Password1234!")
                .build();
        userCreateResponse = UserCreateResponseDto.builder()
                .id(id)
                .username("username1")
                .email("test1@example.com")
                .createdAt(createdAt)
                .build();
        userRole = UserRole.builder()
                .userId(id)
                .roleId(roleId)
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
