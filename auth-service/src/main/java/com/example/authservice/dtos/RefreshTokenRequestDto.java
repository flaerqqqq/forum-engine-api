package com.example.authservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for handling refresh token requests.
 * <p>
 * This class is used to encapsulate the refresh token in HTTP requests when the user
 * requests a new JWT token. The {@code token} field represents the refresh token provided
 * by the client.
 * </p>
 *
 * <p>
 * This DTO is typically used in the {@link com.example.authservice.controllers.AuthController#refresh(RefreshTokenRequestDto)}
 * endpoint to refresh JWT tokens.
 * </p>
 *
 * @see com.example.authservice.controllers.AuthController
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenRequestDto {

    /**
     * The refresh token string provided by the client.
     */
    private String token;
}
