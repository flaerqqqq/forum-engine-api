package com.example.user_service.logs;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.example.user_service.clients.LogClient;
import com.example.user_service.model.User;
import com.example.user_service.repositories.UserRepository;
import com.example.user_service.request.LogMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom log appender that sends log messages to a remote log service.
 * <p>
 * This component extends {@link AppenderBase} to handle log events from Logback and send them
 * to a remote logging service through {@link LogClient}.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class LogAppender extends AppenderBase<ILoggingEvent> {

    private final LogClient logClient;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    @Value("${spring.application.name}")
    private String serviceId;


    /**
     * Processes and sends a log event to the remote log service.
     * <p>
     * This method extracts details from the {@link ILoggingEvent}, constructs a {@link LogMessageDto},
     * and sends it to the remote logging service using the {@link LogClient}.
     * </p>
     *
     * @param iLoggingEvent the log event to process
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
                    .build();
            logClient.log(logMessageDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Builds metadata for the log message.
     * <p>
     * This method retrieves user information from the security context, such as the user ID, and serializes
     * it to a JSON string. If the user is not authenticated, the metadata will contain the username or "anonymousUser".
     * </p>
     *
     * @return the serialized metadata as a JSON string
     * @throws JsonProcessingException if there is an error processing the JSON
     */
    private String buildMetadata() throws JsonProcessingException {
        Map<String, String> metadata = new HashMap<>();

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            if (!username.equals("anonymousUser")) {
                User user = userRepository.findByUsername(username).get();
                metadata.put("userId", user.getId());
            } else {
                metadata.put("userId", username);
            }
        }

        return objectMapper.writeValueAsString(metadata);
    }
}
