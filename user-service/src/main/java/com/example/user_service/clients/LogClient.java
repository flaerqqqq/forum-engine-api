package com.example.user_service.clients;

import com.example.user_service.request.LogMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Feign client interface for interacting with the logging service.
 * <p>
 * This interface defines methods for communicating with the {@code log-service},
 * which is responsible for handling log messages. It uses Feign to simplify
 * HTTP calls to the log service.
 * </p>
 */
@FeignClient(name = "log-service")
public interface LogClient {

    /**
     * Sends a log message to the log service.
     * <p>
     * This method posts a {@link LogMessageDto} to the {@code /api/v1/logs} endpoint
     * of the log service to record log messages.
     * </p>
     *
     * @param logMessage the log message to be sent to the log service
     */
    @PostMapping("/api/v1/logs")
    void log(LogMessageDto logMessage);
}
