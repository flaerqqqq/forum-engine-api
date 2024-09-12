package com.example.logservice.controllers;

import com.example.logservice.dtos.LogMessageDto;
import com.example.logservice.services.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling log-related requests.
 * <p>
 * This controller provides an API endpoint to receive log messages and pass them
 * to the `LogService` for processing.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class LogController {

    /**
     * The service that handles log processing logic.
     */
    private final LogService logService;

    /**
     * Receives a log message and forwards it to the log service for processing.
     *
     * @param logMessage The log message to be processed, passed in the request body.
     */
    @PostMapping
    public void takeLog(@RequestBody LogMessageDto logMessage) {
        logService.log(logMessage);
    }
}
