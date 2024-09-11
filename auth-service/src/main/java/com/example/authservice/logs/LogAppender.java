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

@Component
@RequiredArgsConstructor
public class LogAppender extends AppenderBase<ILoggingEvent> {

    private final LogClient logClient;
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
