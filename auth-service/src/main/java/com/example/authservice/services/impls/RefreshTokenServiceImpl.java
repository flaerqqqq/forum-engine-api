package com.example.authservice.services.impls;

import com.example.authservice.dtos.RefreshTokenDto;
import com.example.authservice.entities.AuthUser;
import com.example.authservice.entities.RefreshToken;
import com.example.authservice.exceptions.UserNotFoundException;
import com.example.authservice.mappers.RefreshTokenMapper;
import com.example.authservice.repositories.AuthUserRepository;
import com.example.authservice.repositories.RefreshTokenRepository;
import com.example.authservice.services.JwtService;
import com.example.authservice.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthUserRepository authUserRepository;
    private final JwtService jwtService;
    private final RefreshTokenMapper refreshTokenMapper;

    @Override
    public RefreshTokenDto generateRefreshToken(String userId) {
        AuthUser authUser = authUserRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id is found: %s".formatted(userId)));

        String refreshJwtToken = jwtService.generateRefreshToken(userId);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(refreshJwtToken)
                .authUser(authUser)
                .build();

        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);

        return refreshTokenMapper.toDto(savedRefreshToken);
    }
}
