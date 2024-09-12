package com.example.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data transfer object representing a user.
 * <p>
 * This class is used to transfer user data between layers of the application. It includes the user's ID, username, email, password,
 * and timestamps for creation and last modification.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    /**
     * The unique identifier for the user.
     */
    private String id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The password of the user.
     * Note: In practice, passwords should not be included in DTOs unless absolutely necessary and should be handled securely.
     */
    private String password;

    /**
     * The timestamp when the user was created.
     */
    private LocalDateTime createdAt;

    /**
     * The timestamp when the user was last modified.
     */
    private LocalDateTime lastModifiedAt;
}