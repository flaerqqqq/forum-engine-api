package com.example.authservice.clients;

import com.example.authservice.request.LogMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "log-service")
public interface LogClient {

    @PostMapping("/api/v1/logs")
    void log(LogMessageDto logMessage);
}
