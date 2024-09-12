package com.example.logservice.services.impl;

import com.example.logservice.dtos.LogMessageDto;
import com.example.logservice.services.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the LogService that handles logging messages at various levels.
 * <p>
 * This service formats the log message and logs it at the appropriate level
 * (ERROR, WARN, INFO, DEBUG, TRACE) using SLF4J.
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final ObjectMapper objectMapper;

    /**
     * Processes the log message and logs it at the appropriate level based on the `level` field.
     *
     * @param logMessage The log message to be processed and logged.
     */
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

    /**
     * Formats the log message into a JSON string including timestamp, serviceId, message,
     * logger, and metadata. The metadata field is parsed from JSON.
     *
     * @param logMessageDto The DTO containing log information.
     * @return A formatted JSON string for the log.
     */
    private String formatLogMessage(LogMessageDto logMessageDto) {
        try {
            Map<String, Object> logData = new HashMap<>();
            logData.put("timestamp", logMessageDto.getTimestamp());
            logData.put("serviceId", logMessageDto.getServiceId());
            logData.put("message", logMessageDto.getMessage());
            logData.put("logger", logMessageDto.getLogger());
            logData.put("metadata", logMessageDto.getMetadata());

            String metadataJson = logMessageDto.getMetadata();
            JsonNode metadataNode = objectMapper.readTree(metadataJson);
            logData.put("metadata", metadataNode);

            return objectMapper.writeValueAsString(logData);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
