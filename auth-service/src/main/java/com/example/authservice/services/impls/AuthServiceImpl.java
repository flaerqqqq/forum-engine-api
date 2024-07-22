package com.example.authservice.services.impls;

import com.example.authservice.clients.UserClient;
import com.example.authservice.clients.dtos.UserServiceCreateRequestDto;
import com.example.authservice.dtos.UserRegisterRequestDto;
import com.example.authservice.dtos.UserRegisterResponseDto;
import com.example.authservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceServiceImpl implements AuthService {

    private final UserClient userClient;

    @Override
    public UserRegisterResponseDto register(UserRegisterRequestDto request) {
        var userServiceCreateRequest = UserServiceCreateRequestDto.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        var userServiceCreateResponse = userClient.create(userServiceCreateRequest);

        return UserRegisterResponseDto.builder().username(userServiceCreateResponse.getBody().getUsername())
                .id(userServiceCreateResponse.getBody().getId())
                .email(userServiceCreateResponse.getBody().getEmail())
                .createdAt(userServiceCreateResponse.getBody().getCreatedAt())
                .build();
    }
}
