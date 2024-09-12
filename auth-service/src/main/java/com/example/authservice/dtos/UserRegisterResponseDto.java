package com.example.authservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data transfer object (DTO) for user registration response containing user details.
 * <p>
 * This DTO is used to provide information about the newly registered user in the response
 * after a successful registration. It includes the user's ID, username, email, and the
 * timestamp when the user was created.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterResponseDto {

    /**
     * The unique identifier of the newly registered user.
     * <p>
     * This ID is generated and assigned by the system upon registration.
     * </p>
     */
    private String id;

    /**
     * The username of the newly registered user.
     * <p>
     * This is the username provided by the user during registration.
     * </p>
     */
    private String username;

    /**
     * The email address of the newly registered user.
     * <p>
     * This is the email address provided by the user during registration.
     * </p>
     */
    private String email;

    /**
     * The timestamp when the user was created in the system.
     * <p>
     * This field indicates when the user account was created.
     * </p>
     */
    private LocalDateTime createdAt;
}
