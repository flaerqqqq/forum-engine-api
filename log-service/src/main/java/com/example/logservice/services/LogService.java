package com.example.logservice.services;

import com.example.logservice.dtos.LogMessageDto;

/**
 * LogService interface that defines the contract for logging messages.
 * This service is intended to be implemented by any class that handles logging functionality.
 */
public interface LogService {

    /**
     * Logs a message at the appropriate level.
     *
     * @param logMessage the DTO containing log details such as level, message, and metadata.
     */
    void log(LogMessageDto logMessage);
}
