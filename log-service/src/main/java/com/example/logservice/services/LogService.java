package com.example.logservice.services;

import com.example.logservice.dtos.LogMessageDto;

/**
 * Service interface for logging messages.
 * Implementations of this interface are responsible for logging messages
 * provided through a {@link LogMessageDto} object.
 */
public interface LogService {

    /**
     * Logs a message with the details provided in the {@link LogMessageDto}.
     *
     * @param logMessage the log message containing information such as timestamp, service ID, log level, etc.
     */
    void log(LogMessageDto logMessage);
}
