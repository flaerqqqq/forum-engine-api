package com.example.authservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object (DTO) for representing a refresh token.
 * <p>
 * This DTO is used to encapsulate details about the refresh token, such as its ID, the token value itself,
 * and the associated user ID. The refresh token allows the client to request a new JWT token
 * without needing to re-authenticate.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenDto {

    /**
     * The unique identifier of the refresh token.
     * <p>
     * This ID is used to uniquely identify the refresh token in the database.
     * </p>
     */
    private Long id;

    /**
     * The refresh token value.
     * <p>
     * This token is used to obtain a new JWT token when the original one expires, allowing for continuous
     * authentication without re-login.
     * </p>
     */
    private String token;

    /**
     * The unique identifier of the user associated with the refresh token.
     * <p>
     * This field links the refresh token to the user who is authorized to request new JWT tokens.
     * </p>
     */
    private String userId;
}
