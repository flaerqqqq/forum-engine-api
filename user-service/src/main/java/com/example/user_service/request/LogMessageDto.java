package com.example.user_service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogMessageDto {

    private LocalDateTime timestamp;

    private String serviceId;

    private String message;

    private String level;

    private String context;

    private String metadata;
}
