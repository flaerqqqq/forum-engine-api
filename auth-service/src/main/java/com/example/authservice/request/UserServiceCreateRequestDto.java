package com.example.authservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for creating a new user in the authentication service.
 * <p>
 * This class encapsulates the necessary information for creating a user, including the username,
 * email, and password.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserServiceCreateRequestDto {

    /**
     * The username of the user to be created.
     */
    private String username;

    /**
     * The email address of the user to be created.
     */
    private String email;

    /**
     * The password for the user to be created.
     */
    private String password;
}
