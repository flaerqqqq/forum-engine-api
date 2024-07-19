package com.example.authservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AuthService authService;

    private UserRegisterRequestDto registerRequest;
    private UserRegisterRequestDto invalidRegisterRequest;
    private UserRegisterResponseDto registerResponse;

    @BeforeEach
    void setUp() {
        registerRequest = UserRegisterRequestDto.builder()
                .username("username1")
                .email("test1@example.com")
                .password("Password1234")
                .build();
        invalidRegisterRequest = UserRegisterRequestDto.builder()
                .username("u")
                .email("test1com")
                .password("pass")
                .build();
        registerResponse = UserRegisterResponseDto.builder()
                .id("uuid")
                .username("username1")
                .email("test1@example.com")
                .password("Password1234")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @AfterEach
    void cleanUp() {
        registerRequest = null;
        registerResponse = null;
    }

    @Test
    void register_shouldReturnResponse_ifDataIsCorrect() throws Exception {
        when(authService.register(any(UserRegisterRequestDto.class))).thenReturn(registerResponse);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(registerRequest))
                )
                .andExpect(jsonPath("$.id").value(registerResponse.getId()))
                .andExpect(jsonPath("$.username").value(registerResponse.getUsername()))
                .andExpect(jsonPath("$.email").value(registerResponse.getEmail()))
                .andExpect(jsonPath("$.createdAt").value(registerResponse.getCreatedAt));
    }

    @Test
    void register_shouldReturn201Status_ifDataIsCorrect() throws Exception {
        when(authService.register(any(UserRegisterRequestDto.class))).thenReturn(registerResponse);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(registerRequest))
                )
                .andExpect(status().isCreated());
    }

    @Test
    void register_shouldReturn400_ifDataIncorrect() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(invalidRegisterRequest))
                )
                .andExpect(status().isBadRequest());
    }
}
