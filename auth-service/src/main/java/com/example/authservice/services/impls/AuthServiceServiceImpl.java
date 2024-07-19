package com.example.authservice.services.impls;

import com.example.authservice.dtos.UserRegisterRequestDto;
import com.example.authservice.dtos.UserRegisterResponseDto;
import com.example.authservice.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceServiceImpl implements AuthService {
    @Override
    public UserRegisterResponseDto register(UserRegisterRequestDto request) {
        return null;
    }
}
