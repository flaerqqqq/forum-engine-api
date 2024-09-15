package com.example.authservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object (DTO) for the response containing a JWT tokens after a successful login.
 * <p>
 * This DTO is used to encapsulate the JWT tokens that is returned to the client after
 * successful authentication. The token can be used for subsequent requests to access protected resources.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginJwtResponseDto {

    /**
     * The JWT token issued after successful login.
     * <p>
     * This token is used for authenticating subsequent requests and accessing protected resources.
     * </p>
     */
    private String token;

    /**
     * The refresh token issued along with the JWT token.
     * <p>
     * This token is used to obtain a new JWT token when the original one expires, without requiring the user to log in again.
     * </p>
     */
    private String refreshToken;
}
