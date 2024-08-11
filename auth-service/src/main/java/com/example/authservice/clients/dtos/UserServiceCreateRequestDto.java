package com.example.authservice.clients.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserServiceCreateRequestDto {

    private String username;

    private String email;

    private String password;
}
