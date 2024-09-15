package com.example.authservice.controllers;

import com.example.authservice.dtos.*;
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

/**
 * REST controller for handling authentication-related requests.
 * <p>
 * Provides endpoints for user registration, login, and JWT token refresh.
 * This controller handles HTTP POST requests and interacts with the {@link AuthService}
 * to perform authentication operations.
 * </p>
 *
 * <ul>
 *   <li>Handles user registration by creating a new account and returning user details.</li>
 *   <li>Handles user login and returns a JWT token along with user details.</li>
 *   <li>Handles token refresh by issuing a new JWT token based on a valid refresh token.</li>
 * </ul>
 *
 * <p>
 * Annotations:
 * <ul>
 *   <li>{@code @RestController}: Marks the class as a Spring MVC controller that returns JSON responses.</li>
 *   <li>{@code @RequestMapping("/api/v1/auth")}: Maps HTTP requests to "/api/v1/auth" to this controller.</li>
 *   <li>{@code @RequiredArgsConstructor}: Automatically generates a constructor for final fields (AuthService).</li>
 *   <li>{@code @Slf4j}: Provides logging capabilities using SLF4J.</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    /**
     * Handles user registration requests.
     * This endpoint accepts user registration details and uses {@link AuthService} to register a new user.
     * The response includes user registration details and has a status of {@link HttpStatus#CREATED}.
     *
     * @param request the user registration details encapsulated in a {@link UserRegisterRequestDto}
     * @return a {@link ResponseEntity} containing the {@link UserRegisterResponseDto} with user registration details
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> register(@RequestBody @Valid UserRegisterRequestDto request) {
        UserRegisterResponseDto response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Handles user login requests.
     * This endpoint accepts login credentials and uses {@link AuthService} to authenticate the user.
     * The response includes a JWT token and user details, with a status of {@link HttpStatus#OK}.
     *
     * @param request the login credentials encapsulated in a {@link LoginRequestDto}
     * @return a {@link ResponseEntity} containing the {@link LoginJwtResponseDto} with the JWT token and user details
     */
    @PostMapping("/login")
    public ResponseEntity<LoginJwtResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginJwtResponseDto response = authService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Handles JWT refresh token requests.
     * <p>
     * This endpoint accepts a refresh token and issues a new JWT token if the refresh token is valid.
     * The response includes a new JWT token and user details, with a status of {@link HttpStatus#OK}.
     * </p>
     *
     * @param request the refresh token encapsulated in a {@link RefreshTokenRequestDto}
     * @return a {@link ResponseEntity} containing the {@link LoginJwtResponseDto} with the new JWT token and user details
     */
    @PostMapping("/refresh")
    public ResponseEntity<LoginJwtResponseDto> refresh(@RequestBody RefreshTokenRequestDto request) {
        LoginJwtResponseDto response = authService.refresh(request.getToken());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
