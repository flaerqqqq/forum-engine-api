package com.example.authservice.clients;

import com.example.authservice.request.UserServiceCreateRequestDto;
import com.example.authservice.response.UserServiceResponseDto;
import com.example.authservice.config.FeignUserServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client for interacting with the "user-service" microservice.
 * This client provides methods for retrieving and creating user records in the "user-service".
 * <p>
 * - Uses {@link FeignClient} to communicate with the "user-service".
 * - Configuration is provided by {@link FeignUserServiceConfig}.
 * - The base URL for the service is specified in the application configuration.
 * </p>
 *
 * Annotations:
 * - {@code @FeignClient}: Indicates that this interface is a Feign client for the "user-service".
 * - {@code @GetMapping}: Maps to the GET request for retrieving user details.
 * - {@code @PostMapping}: Maps to the POST request for creating a new user.
 */
@FeignClient(name = "user-service", url = "${application.config.user-url}", configuration = FeignUserServiceConfig.class)
public interface UserClient {

    /**
     * Retrieves a user by ID from the "user-service".
     * This method performs a GET request to the "/{id}" endpoint of the "user-service".
     *
     * @param id the ID of the user to retrieve
     * @return a {@link ResponseEntity} containing the {@link UserServiceResponseDto} with user details
     */
    @GetMapping("/{id}")
    ResponseEntity<UserServiceResponseDto> getById(@PathVariable String id);

    /**
     * Creates a new user in the "user-service".
     * This method performs a POST request to the root endpoint of the "user-service" with the user creation request body.
     *
     * @param request the user creation request encapsulated in a {@link UserServiceCreateRequestDto}
     * @return a {@link ResponseEntity} containing the {@link UserServiceResponseDto} with the created user's details
     */
    @PostMapping
    ResponseEntity<UserServiceResponseDto> create(@RequestBody UserServiceCreateRequestDto request);
}
