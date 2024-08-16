package com.example.logservice.controllers;

import com.example.logservice.dtos.LogMessageDto;
import com.example.logservice.services.LogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @PostMapping
    public void takeLog(@RequestBody LogMessageDto logMessage) {
        logService.log(logMessage);
    }
}
