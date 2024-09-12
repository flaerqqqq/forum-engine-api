package com.example.logservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data transfer object for log messages in the log service.
 * <p>
 * This class represents the structure of a log message, which includes details like the
 * timestamp, service identifier, log message, level of logging, logger name, and additional metadata.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogMessageDto {

    /**
     * The timestamp when the log message was created.
     */
    private LocalDateTime timestamp;

    /**
     * The unique identifier of the service that generated the log message.
     */
    private String serviceId;

    /**
     * The log message content.
     */
    private String message;

    /**
     * The severity level of the log (e.g., INFO, DEBUG, ERROR).
     */
    private String level;

    /**
     * The name of the logger that produced the log message.
     */
    private String logger;

    /**
     * Additional metadata associated with the log message.
     */
    private String metadata;
}
