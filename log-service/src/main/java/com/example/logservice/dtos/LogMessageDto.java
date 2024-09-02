package com.example.logservice.dtos;

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

    private String logger;

    private String metadata;

}
