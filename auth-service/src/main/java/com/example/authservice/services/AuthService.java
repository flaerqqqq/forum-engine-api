package com.example.authservice.services;

import com.example.authservice.dtos.LoginJwtResponseDto;
import com.example.authservice.dtos.LoginRequestDto;
import com.example.authservice.dtos.UserRegisterRequestDto;
import com.example.authservice.dtos.UserRegisterResponseDto;

/**
 * Service interface for handling authentication and user registration operations.
 * <p>
 * This interface defines methods for user registration, login, and refresh token operations,
 * which are typically implemented by classes handling authentication-related logic in the application.
 * </p>
 */
public interface AuthService {

    /**
     * Registers a new user based on the provided registration request data.
     * <p>
     * This method processes the registration request, creates a new user, and assigns
     * appropriate roles to the user. The response includes the details of the registered user.
     * </p>
     *
     * @param request the {@link UserRegisterRequestDto} containing user registration details
     * @return the {@link UserRegisterResponseDto} containing details of the registered user
     */
    UserRegisterResponseDto register(UserRegisterRequestDto request);

    /**
     * Authenticates a user and generates a JWT token based on the provided login request data.
     * <p>
     * This method validates the login credentials, performs authentication, and returns
     * a JWT token if the login is successful.
     * </p>
     *
     * @param request the {@link LoginRequestDto} containing user login credentials
     * @return the {@link LoginJwtResponseDto} containing the JWT token for the authenticated user
     */
    LoginJwtResponseDto login(LoginRequestDto request);

    /**
     * Refreshes the JWT token based on the provided refresh token.
     * <p>
     * This method validates the refresh token, generates a new JWT token, and returns both the new JWT token
     * and a new refresh token.
     * </p>
     *
     * @param refreshToken the refresh token used to generate a new JWT token
     * @return a {@link LoginJwtResponseDto} containing the new JWT token and refresh token
     */
    LoginJwtResponseDto refresh(String refreshToken);
}