package com.example.authservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data transfer object for representing a log message.
 * <p>
 * This class encapsulates information related to a log message, including the timestamp, service ID,
 * message content, logger name, log level, context, and any additional metadata.
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
     * The identifier of the service that generated the log message.
     */
    private String serviceId;

    /**
     * The actual log message content.
     */
    private String message;

    /**
     * The name of the logger that generated the log message.
     */
    private String logger;

    /**
     * The log level (e.g., INFO, WARN, ERROR) associated with the log message.
     */
    private String level;

    /**
     * The context in which the log message was generated.
     */
    private String context;

    /**
     * Additional metadata related to the log message.
     */
    private String metadata;
}
