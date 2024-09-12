package com.example.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for creating a new user.
 * <p>
 * This class is used to encapsulate the data required for user creation. It includes fields for username, email, and password.
 * The fields are validated to ensure they meet specific criteria:
 * <ul>
 *     <li>Username cannot be null or blank.</li>
 *     <li>Email must match a standard email format.</li>
 *     <li>Password must be at least 8 characters long, contain at least one letter, one number, and one special symbol.</li>
 * </ul>
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequestDto {

    /**
     * The username of the new user.
     * Must not be null or blank.
     */
    @NotBlank(message = "Username cannot be null")
    private String username;

    /**
     * The email address of the new user.
     * Must match the pattern: test@example.com.
     */
    @Pattern(
            regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
            message = "Email should match the pattern: test@example.com"
    )
    private String email;

    /**
     * The password for the new user.
     * Must be at least 8 characters long and contain at least one letter, one number, and one special symbol.
     */
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password should contain the following:\n" +
                    "- At least 8 characters.\n" +
                    "- At least one letter.\n" +
                    "- At least one number.\n" +
                    "- At least one special symbol."
    )
    private String password;
}
