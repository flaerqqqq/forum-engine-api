package com.example.authservice.clients;

import com.example.authservice.clients.dtos.UserServiceCreateRequestDto;
import com.example.authservice.clients.dtos.UserServiceResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${application.config.user-url}")
public interface UserClient {

    @GetMapping("/{id}")
    ResponseEntity<UserServiceResponseDto> getById(@PathVariable String id);

    @PostMapping
    ResponseEntity<UserServiceResponseDto> create(@RequestBody UserServiceCreateRequestDto request);
}
