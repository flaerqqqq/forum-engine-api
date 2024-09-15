package com.example.authservice.services;

import com.example.authservice.dtos.RefreshTokenDto;
import com.example.authservice.entities.AuthUser;
import com.example.authservice.entities.RefreshToken;
import com.example.authservice.exceptions.UserNotFoundException;
import com.example.authservice.mappers.RefreshTokenMapper;
import com.example.authservice.repositories.AuthUserRepository;
import com.example.authservice.repositories.RefreshTokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RefreshTokenServiceTest {

    @MockBean
    AuthUserRepository authUserRepository;

    @MockBean
    JwtService jwtService;

    @MockBean
    RefreshTokenRepository refreshTokenRepository;

    @MockBean
    RefreshTokenMapper refreshTokenMapper;

    @Autowired
    RefreshTokenService refreshTokenService;

    private final String userId = "id";
    private final String jwtToken = "jwtToken";
    private AuthUser authUser;
    private RefreshToken refreshToken;
    private RefreshTokenDto refreshTokenDto;

    @BeforeEach
    void initialization() {
        authUser = AuthUser.builder()
                .id(userId)
                .userRoles(null)
                .username("username")
                .password("1234")
                .build();

        refreshToken = RefreshToken.builder()
                .id(1234L)
                .token(jwtToken)
                .authUser(authUser)
                .build();

        refreshTokenDto = RefreshTokenDto.builder()
                .id(1234L)
                .token(jwtToken)
                .userId(userId)
                .build();
    }

    @AfterEach
    void cleanUp() {
        authUser = null;
        refreshToken = null;
        refreshTokenDto = null;
    }

    @Test
    public void generateRefreshToken_shouldReturnToken_ifUserExists() {
        when(authUserRepository.findById(anyString())).thenReturn(Optional.of(authUser));
        when(jwtService.generateRefreshToken(anyString())).thenReturn(jwtToken);
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(refreshToken);
        when(refreshTokenMapper.toDto(any(RefreshToken.class))).thenReturn(refreshTokenDto);

        RefreshTokenDto actualResult = refreshTokenService.generateRefreshToken(userId);

        assertThat(actualResult).isEqualTo(refreshTokenDto);
    }

    @Test
    public void generateRefreshToken_shouldThrow_ifUserDoesNotExist() {
        when(authUserRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                refreshTokenService.generateRefreshToken(userId));
    }
}
