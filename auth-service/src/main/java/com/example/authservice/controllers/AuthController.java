package com.example.authservice.controllers;

import com.example.authservice.dtos.LoginJwtResponseDto;
import com.example.authservice.dtos.LoginRequestDto;
import com.example.authservice.dtos.UserRegisterRequestDto;
import com.example.authservice.dtos.UserRegisterResponseDto;
import com.example.authservice.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> register(@RequestBody @Valid UserRegisterRequestDto request) {
        UserRegisterResponseDto response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginJwtResponseDto> login(@RequestBody LoginRequestDto request) {
        log.info("SOMETHING HAPPENED");
        LoginJwtResponseDto response = authService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
