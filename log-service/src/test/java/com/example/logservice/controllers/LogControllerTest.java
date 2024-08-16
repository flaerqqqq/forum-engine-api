package com.example.logservice.controllers;

import com.example.logservice.dtos.LogMessageDto;
import com.example.logservice.services.impl.LogServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class LogControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    LogServiceImpl logService;

    private LogMessageDto logMessage;
    private LogMessageDto invalidLogMessage;


    @BeforeEach
    void initialize() {
        logMessage = LogMessageDto.builder()
                .timestamp(LocalDateTime.now())
                .serviceId("serviceId")
                .message("some message")
                .level("INFO")
                .context("some context data")
                .metadata("some metadata")
                .build();
        logMessage = LogMessageDto.builder()
                .build();
    }

    @Test
    void takeLog_shouldReturn200() throws Exception {
        mockMvc.perform(post("/api/v1/logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(logMessage))
        ).andExpect(status().isOk());
    }
}
