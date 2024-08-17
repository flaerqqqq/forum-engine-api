package com.example.logservice.services.impl;

import com.example.logservice.dtos.LogMessageDto;
import com.example.logservice.services.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Override
    public void log(LogMessageDto logMessage) {
        String message = formatLogMessage(logMessage);

        switch (logMessage.getLevel().toUpperCase()) {
            case "ERROR":
                log.error(message);
                break;
            case "WARN":
                log.warn(message);
                break;
            case "INFO":
                log.info(message);
                break;
            case "DEBUG":
                log.debug(message);
                break;
            case "TRACE":
                log.trace(message);
                break;
            default:
                log.info("Unknown log level: {}. Message: {}", logMessage.getLevel(), message);
                break;
        }
    }

    private String formatLogMessage(LogMessageDto logMessageDto) {
        return String.format(
                "Timestamp: %s, ServiceId: %s, Message: %s, Context: %s, Metadata: %s",
                logMessageDto.getTimestamp(),
                logMessageDto.getServiceId(),
                logMessageDto.getMessage(),
                logMessageDto.getContext(),
                logMessageDto.getMetadata()
        );
    }
}
