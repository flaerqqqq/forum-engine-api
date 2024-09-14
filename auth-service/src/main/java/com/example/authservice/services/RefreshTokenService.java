package com.example.authservice.services;

import com.example.authservice.dtos.RefreshTokenDto;

public interface RefreshTokenService {

    RefreshTokenDto generateRefreshToken(String userId);
}
