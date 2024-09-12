package com.example.authservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object (DTO) for the response containing a JWT token after a successful login.
 * <p>
 * This DTO is used to encapsulate the JWT token that is returned to the client after
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
}
