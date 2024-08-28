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

@Component
@RequiredArgsConstructor
public class LogAppender extends AppenderBase<ILoggingEvent> {

    private final LogClient logClient;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    @Value("${spring.application.name}")
    private String serviceId;

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
