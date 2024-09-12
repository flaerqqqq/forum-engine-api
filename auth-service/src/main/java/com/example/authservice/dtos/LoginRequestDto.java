package com.example.authservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object (DTO) for login request containing user credentials.
 * <p>
 * This DTO is used to encapsulate the login credentials provided by the user
 * when making a request to authenticate and obtain a JWT token.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDto {

    /**
     * The username of the user trying to log in.
     * <p>
     * This field is used to identify the user for authentication purposes.
     * </p>
     */
    private String username;

    /**
     * The password of the user trying to log in.
     * <p>
     * This field is used in conjunction with the username to authenticate the user.
     * </p>
     */
    private String password;
}
