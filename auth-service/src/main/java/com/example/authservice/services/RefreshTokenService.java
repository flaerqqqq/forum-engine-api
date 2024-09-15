package com.example.authservice.services;

import com.example.authservice.dtos.RefreshTokenDto;

/**
 * Service interface for managing refresh tokens.
 * <p>
 * This interface defines the contract for generating refresh tokens, which allows clients to request a new JWT token
 * without requiring re-authentication. The implementation of this interface handles the creation and persistence
 * of the refresh token for a specified user.
 * </p>
 */
public interface RefreshTokenService {

    /**
     * Generates a new refresh token for the specified user.
     * <p>
     * This method creates a refresh token for the given user ID, which can be used to obtain a new JWT token
     * when the current token expires.
     * </p>
     *
     * @param userId the unique identifier of the user for whom the refresh token is generated
     * @return the {@link RefreshTokenDto} containing the generated refresh token
     */
    RefreshTokenDto generateRefreshToken(String userId);
}
