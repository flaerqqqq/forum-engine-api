package com.example.authservice.clients;

import com.example.authservice.request.LogMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Feign client for interacting with the external log service.
 * This client sends log messages to the "log-service" microservice.
 * <p>
 * - Uses {@link FeignClient} to communicate with the "log-service".
 * - Sends log messages to the endpoint "/api/v1/logs".
 * </p>
 *
 * Annotations:
 * - {@code @FeignClient}: Indicates that this interface is a Feign client for the "log-service" microservice.
 */
@FeignClient(name = "log-service")
public interface LogClient {

    /**
     * Sends a log message to the external log service.
     * This method performs a POST request to the "/api/v1/logs" endpoint of the "log-service".
     *
     * @param logMessage the log message to be sent, encapsulated in a {@link LogMessageDto}
     */
    @PostMapping("/api/v1/logs")
    void log(LogMessageDto logMessage);
}
