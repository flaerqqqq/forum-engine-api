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
 * Implementation of the LogService interface, responsible for logging messages with
 * different levels (ERROR, WARN, INFO, DEBUG, TRACE) using SLF4J.
 * This service formats log messages into a structured JSON format.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final ObjectMapper objectMapper;

    /**
     * Logs a message at the specified log level from the LogMessageDto.
     *
     * @param logMessage the log message containing information such as timestamp, service ID, level, etc.
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
     * Formats a log message into a structured JSON string. If the metadata field is a valid JSON string,
     * it is parsed into a JSON object and included in the final log message.
     *
     * @param logMessageDto the log message data transfer object containing the log information.
     * @return the formatted log message as a JSON string.
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
