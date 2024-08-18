package com.example.logservice.services.impl;

import com.example.logservice.dtos.LogMessageDto;
import com.example.logservice.services.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final ObjectMapper objectMapper;

    @Override
    public void log(LogMessageDto logMessage) {
        String message = formatLogMessage(logMessage);

        switch (logMessage.getLevel().toUpperCase()) {
            case "ERROR":
                log.error(message);
                break;
            case "WARN":
                log.warn(message);
                break;
            case "INFO":
                log.info(message);
                break;
            case "DEBUG":
                log.debug(message);
                break;
            case "TRACE":
                log.trace(message);
                break;
            default:
                log.info("Unknown log level: {}. Message: {}", logMessage.getLevel(), message);
                break;
        }
    }

    private String formatLogMessage(LogMessageDto logMessageDto) {
        try {
            Map<String, Object> logData = new HashMap<>();
            logData.put("timestamp", logMessageDto.getTimestamp());
            logData.put("serviceId", logMessageDto.getServiceId());
            logData.put("message", logMessageDto.getMessage());
            logData.put("context", logMessageDto.getContext());
            logData.put("metadata", logMessageDto.getMetadata());

            return objectMapper.writeValueAsString(logData);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
