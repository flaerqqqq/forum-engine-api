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

import java.util.Optional;

/**
 * Implementation of the {@link RefreshTokenService} interface.
 * <p>
 * This service provides functionality to generate a new refresh token for an authenticated user. It interacts
 * with the {@link AuthUserRepository} to retrieve the user, the {@link JwtService} to generate a refresh token,
 * and the {@link RefreshTokenRepository} to save the token in the database. The generated refresh token is then
 * mapped to a {@link RefreshTokenDto} for the response.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthUserRepository authUserRepository;
    private final JwtService jwtService;
    private final RefreshTokenMapper refreshTokenMapper;

    /**
     * Generates a refresh token for the specified user.
     * <p>
     * This method retrieves the user from the database using the provided user ID. If the user is found,
     * a new refresh token is generated using the {@link JwtService}, saved to the database, and returned as a DTO.
     * </p>
     *
     * @param userId the unique identifier of the user for whom the refresh token is generated
     * @return the {@link RefreshTokenDto} containing the generated refresh token
     * @throws UserNotFoundException if no user is found with the given ID
     */
    @Override
    public RefreshTokenDto generateRefreshToken(String userId) {
        AuthUser authUser = authUserRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id is found: %s".formatted(userId)));

        RefreshToken refreshToken;
        String refreshJwtToken = jwtService.generateRefreshToken(userId);

        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserId(authUser.getId());
        if (existingToken.isPresent()) {
            refreshToken = existingToken.get();
            refreshToken.setToken(refreshJwtToken);
        } else {
            refreshToken = RefreshToken.builder()
                    .token(refreshJwtToken)
                    .authUser(authUser)
                    .build();
        }

        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);

        return refreshTokenMapper.toDto(savedRefreshToken);
    }
}