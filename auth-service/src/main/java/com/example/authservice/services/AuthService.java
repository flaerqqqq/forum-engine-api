package com.example.authservice.services;

import com.example.authservice.dtos.LoginJwtResponseDto;
import com.example.authservice.dtos.LoginRequestDto;
import com.example.authservice.dtos.UserRegisterRequestDto;
import com.example.authservice.dtos.UserRegisterResponseDto;

public interface AuthService {

    UserRegisterResponseDto register(UserRegisterRequestDto request);

    LoginJwtResponseDto login(LoginRequestDto request);
}
