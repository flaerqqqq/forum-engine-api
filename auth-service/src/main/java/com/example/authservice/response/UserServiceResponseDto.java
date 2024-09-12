package com.example.authservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data transfer object for representing a user in the authentication service response.
 * <p>
 * This class is used to encapsulate the response data of a user, including the user ID, username,
 * email, and the creation timestamp.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserServiceResponseDto {

    /**
     * The unique identifier of the user.
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
