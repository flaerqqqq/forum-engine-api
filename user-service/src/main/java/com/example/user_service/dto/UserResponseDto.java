package com.example.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data transfer object representing the response data for a user.
 * <p>
 * This class is used to encapsulate user information that is sent in responses, excluding sensitive information like passwords.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

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
     * The timestamp when the user was created.
     */
    private LocalDateTime createdAt;
}
