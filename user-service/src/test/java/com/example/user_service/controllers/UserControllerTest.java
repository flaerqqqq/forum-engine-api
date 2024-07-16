package com.example.user_service.controllers;

import com.example.user_service.dto.UserCreateRequestDto;
import com.example.user_service.dto.UserDto;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.mappers.UserMapper;
import com.example.user_service.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    UserMapper userMapper;

    private UserCreateRequestDto userCreateRequestDto;
    private UserResponseDto userResponseDto;
    private UserDto userDto;

    @BeforeEach
    public void setup() {
        userCreateRequestDto = UserCreateRequestDto.builder()
                .username("test")
                .email("test@example.com")
                .password("Passss123!")
                .build();

        userResponseDto = UserResponseDto.builder()
                .id("uuid")
                .username("test")
                .email("test@example.com")
                .build();

        userDto = UserDto.builder()
                .id("uuid")
                .username("test")
                .email("test@example.com")
                .password("Passss123!")
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }

    @AfterEach
    public void cleanup() {
        userCreateRequestDto = null;
        userResponseDto = null;
    }

    @Test
    void create_shouldReturnCreatedUserData_whenValidRequestData() throws Exception {
        when(userMapper.toDto(any(UserCreateRequestDto.class))).thenReturn(userDto);
        when(userMapper.toResponseDto(any(UserDto.class))).thenReturn(userResponseDto);
        when(userService.create(any(UserDto.class))).thenReturn(userDto);

        MvcResult result = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateRequestDto))
        ).andReturn();

        String jsonString = result.getResponse().getContentAsString();
        UserResponseDto actualResponse = objectMapper.readValue(jsonString, UserResponseDto.class);

        assertThat(actualResponse).isEqualTo(userResponseDto);
    }

    @Test
    void create_shouldReturn201Status_whenValidRequestData() throws Exception {
        when(userMapper.toDto(any(UserCreateRequestDto.class))).thenReturn(userDto);
        when(userMapper.toResponseDto(any(UserDto.class))).thenReturn(userResponseDto);
        when(userService.create(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateRequestDto))
        ).andExpect(status().isCreated());
    }

    @Test
    void getById_shouldReturnUserData_whenIdIsCorrect() throws Exception {
        when(userMapper.toResponseDto(any(UserDto.class))).thenReturn(userResponseDto);
        when(userService .getById(anyString())).thenReturn(userDto);

        MvcResult result = mockMvc.perform(get("/api/v1/users//{id}", userDto.getId()))
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        UserResponseDto actualResponse = objectMapper.readValue(jsonString, UserResponseDto.class);

        assertThat(actualResponse).isEqualTo(userResponseDto);
    }

    @Test
    void getById_shouldReturn200Status_whenIdIsCorrect() throws Exception {
        when(userMapper.toResponseDto(any(UserDto.class))).thenReturn(userResponseDto);
        when(userService.getById(anyString())).thenReturn(userDto);

        mockMvc.perform(get("/api/v1/users/{id}", userDto.getId()))
                .andExpect(status().isOk());

    }

    @Test
    void getAll_shouldReturnPageOfUsers() throws Exception {
        Page<UserDto> pageOfUsers = new PageImpl<>(List.of(userDto));
        Page<UserResponseDto> pageOfResponses = new PageImpl<>(List.of(userResponseDto));

        when(userMapper.toResponseDto(any(UserDto.class))).thenReturn(userResponseDto);
        when(userService.getAll(any(Pageable.class))).thenReturn(pageOfUsers);

        MvcResult result = mockMvc.perform(get("/api/v1/users"))
                .andExpect(jsonPath("$.content[0].id").value("uuid"))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(1))
                .andReturn();
    }

    @Test
    void getAll_shouldReturn200Status_ifThereAtLeastOneUser() throws Exception {
        Page<UserDto> pageOfUsers = new PageImpl<>(List.of(userDto));
        Page<UserResponseDto> pageOfResponses = new PageImpl<>(List.of(userResponseDto));

        when(userMapper.toResponseDto(any(UserDto.class))).thenReturn(userResponseDto);
        when(userService.getAll(any(Pageable.class))).thenReturn(pageOfUsers);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk());
    }

    @Test
    void getAll_shouldReturn204Status_ifThereNoUsers() throws Exception{
        Page<UserDto> pageOfUsers = new PageImpl<>(Collections.emptyList());
        Page<UserResponseDto> pageOfResponses = new PageImpl<>(List.of(userResponseDto));

        when(userMapper.toResponseDto(any(UserDto.class))).thenReturn(userResponseDto);
        when(userService.getAll(any(Pageable.class))).thenReturn(pageOfUsers);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_shouldReturn204status_whenUserDeleted() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", userDto.getId()))
                .andExpect(status().isNoContent());
    }

}
