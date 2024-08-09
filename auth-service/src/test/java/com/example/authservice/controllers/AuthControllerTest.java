package com.example.authservice.controllers;

import com.example.authservice.dtos.LoginJwtResponseDto;
import com.example.authservice.dtos.LoginRequestDto;
import com.example.authservice.dtos.UserRegisterRequestDto;
import com.example.authservice.dtos.UserRegisterResponseDto;
import com.example.authservice.exceptions.IncorrectPasswordException;
import com.example.authservice.exceptions.UserNotFoundException;
import com.example.authservice.services.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
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
    private LoginRequestDto validLoginRequest;
    private LoginRequestDto invalidLoginRequest;
    private LoginJwtResponseDto expectedJwtResponseDto;

    @BeforeEach
    void setUp() {
        registerRequest = UserRegisterRequestDto.builder()
                .username("username1")
                .email("test1@example.com")
                .password("Password1234!")
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
                .createdAt(LocalDateTime.now())
                .build();
        validLoginRequest = LoginRequestDto.builder()
                .username("test")
                .password("test")
                .build();
        invalidLoginRequest = LoginRequestDto.builder()
                .username("tt")
                .password("tt")
                .build();
        expectedJwtResponseDto = LoginJwtResponseDto.builder()
                .token("token")
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

        MvcResult result = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest))
        ).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        UserRegisterResponseDto actualResponse = objectMapper.readValue(jsonString, UserRegisterResponseDto.class);

        assertThat(actualResponse).isEqualTo(registerResponse);

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

    @Test
    void login_shouldReturnValidJson_ifUserLoginDataCorrect() throws Exception {
        when(authService.login(any(LoginRequestDto.class))).thenReturn(expectedJwtResponseDto);

        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(validLoginRequest))
        ).andReturn();

        String body = result.getResponse().getContentAsString();

        LoginJwtResponseDto actualJwtResponseDto = objectMapper.readValue(body, LoginJwtResponseDto.class);

        assertThat(actualJwtResponseDto).isEqualTo(expectedJwtResponseDto);
    }

    @Test
    void login_shouldReturn200StatusCode_ifUserLoginDataCorrect() throws Exception {
        when(authService.login(any(LoginRequestDto.class))).thenReturn(expectedJwtResponseDto);

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(validLoginRequest))
        ).andExpect(status().isOk());
    }

    @Test
    void login_shouldReturn401StatusCode_ifUsernameIncorrect() throws Exception {
        when(authService.login(any(LoginRequestDto.class))).thenThrow(UserNotFoundException.class);

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(invalidLoginRequest))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void login_shouldReturn401StatusCode_ifPasswordIncorrect() throws Exception {
        when(authService.login(any(LoginRequestDto.class))).thenThrow(IncorrectPasswordException.class);
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(invalidLoginRequest))
        ).andExpect(status().isUnauthorized());
    }
}
