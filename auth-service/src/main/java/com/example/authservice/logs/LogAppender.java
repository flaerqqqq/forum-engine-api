package com.example.authservice.logs;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.example.authservice.clients.LogClient;
import com.example.authservice.request.LogMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom log appender for sending log messages to an external logging service.
 * This appender captures logging events and formats them into a structured
 * {@link LogMessageDto} object before sending it to a remote log service via the {@link LogClient}.
 * <p>
 * - Logs include metadata such as IP address and timestamp.
 * - Uses {@link ObjectMapper} to serialize metadata into JSON format.
 * </p>
 *
 * Annotations:
 * - {@code @Component}: Marks this class as a Spring component for dependency injection.
 * - {@code @RequiredArgsConstructor}: Automatically generates a constructor for final fields (LogClient, ObjectMapper, HttpServletRequest).
 */
@Component
@RequiredArgsConstructor
public class LogAppender extends AppenderBase<ILoggingEvent> {

    private final LogClient logClient;
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    @Value("${spring.application.name}")
    private String serviceId;

    /**
     * Appends a logging event by sending it to an external log service.
     * This method formats the log event into a {@link LogMessageDto} object,
     * which includes a timestamp, service ID, log message, metadata, log level, and logger name.
     *
     * @param iLoggingEvent the logging event captured by the appender
     */
    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        try {
            LogMessageDto logMessageDto = LogMessageDto.builder()
                    .timestamp(LocalDateTime.ofInstant(new Date(iLoggingEvent.getTimeStamp()).toInstant(), ZoneId.systemDefault()))
                    .serviceId(serviceId)
                    .message(iLoggingEvent.getMessage())
                    .metadata(buildMetadata())
                    .level(iLoggingEvent.getLevel().toString())
                    .context("null")
                    .logger(iLoggingEvent.getLoggerName())
                    .build();
            logClient.log(logMessageDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Builds metadata for the log message, including the client's IP address.
     * This method retrieves the client's IP address from the {@link HttpServletRequest} and serializes the metadata into JSON format.
     *
     * @return a JSON string representing the metadata
     * @throws JsonProcessingException if there is an error serializing the metadata
     */
    private String buildMetadata() throws JsonProcessingException {
        Map<String, String> metadata = new HashMap<>();

        if (request != null) {
            try {
                String addr = request.getRemoteAddr();
                metadata.put("IP_ADDRESS", addr);
            } catch (Exception ex) {
                metadata.put("IP_ADDRESS", "no present");
            }
        }

        return objectMapper.writeValueAsString(metadata);
    }
}
