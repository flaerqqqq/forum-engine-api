package com.example.user_service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data transfer object for logging messages.
 * <p>
 * This class is used to encapsulate log message details that are sent to the logging service. It includes information such as
 * the timestamp of the log, the service ID, the log message, the logger name, log level, context, and metadata.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogMessageDto {


    /**
     * The timestamp of the log message.
     */
    private LocalDateTime timestamp;

    /**
     * The identifier of the service generating the log message.
     */
    private String serviceId;

    /**
     * The actual log message.
     */
    private String message;

    /**
     * The name of the logger that generated the log message.
     */
    private String logger;

    /**
     * The level of the log message (e.g., INFO, ERROR).
     */
    private String level;

    /**
     * The context of the log message.
     */
    private String context;

    /**
     * Additional metadata associated with the log message.
     */
    private String metadata;
}
