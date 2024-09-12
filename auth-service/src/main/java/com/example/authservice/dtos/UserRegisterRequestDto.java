package com.example.authservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object (DTO) for user registration request containing user details.
 * <p>
 * This DTO is used to capture the information required for user registration,
 * including username, email, and password. It ensures that the provided data
 * meets specified validation constraints.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequestDto {

    /**
     * The username of the user registering an account.
     * <p>
     * This field cannot be empty or null. It is used to identify the user within the system.
     * </p>
     */
    @NotBlank(message = "Username shouldn't be empty or null")
    private String username;

    /**
     * The email address of the user registering an account.
     * <p>
     * This field must match the pattern of a valid email address, e.g., test@example.com.
     * </p>
     */
    @Pattern(
            regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
            message = "Email should match the pattern: test@example.com"
    )
    private String email;

    /**
     * The password chosen by the user for their account.
     * <p>
     * This field must contain at least 8 characters, including one letter, one number, and one special symbol.
     * </p>
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