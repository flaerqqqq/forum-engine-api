package com.example.user_service.clients;

import com.example.user_service.request.LogMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "log-service")
public interface LogClient {

    @PostMapping("/logs")
    void log(LogMessageDto logMessage);
}
