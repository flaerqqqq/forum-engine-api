package com.example.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequestDto {

    @NotBlank(message = "Username cannot be null")
    private String username;

    @Pattern(
            regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
            message = "Email should match the pattern: test@example.com"
    )
    private String email;

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
