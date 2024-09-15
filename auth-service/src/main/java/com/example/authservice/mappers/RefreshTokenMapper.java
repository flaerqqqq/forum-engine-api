package com.example.authservice.mappers;

import com.example.authservice.config.MapperConfig;
import com.example.authservice.dtos.RefreshTokenDto;
import com.example.authservice.entities.AuthUser;
import com.example.authservice.entities.RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between {@link RefreshToken} entity and {@link RefreshTokenDto}.
 * <p>
 * This interface uses MapStruct to automatically generate the implementation for mapping between
 * the {@link RefreshToken} entity and its corresponding Data Transfer Object (DTO). It also includes a custom
 * mapping for extracting the user ID from the associated {@link AuthUser}.
 * </p>
 */
@Mapper(config = MapperConfig.class)
public interface RefreshTokenMapper {

    /**
     * Maps a {@link RefreshToken} entity to a {@link RefreshTokenDto}.
     * <p>
     * The {@code userId} field in the {@link RefreshTokenDto} is mapped from the {@link AuthUser}'s ID
     * associated with the refresh token.
     * </p>
     *
     * @param refreshToken the {@link RefreshToken} entity to be mapped
     * @return the mapped {@link RefreshTokenDto}
     */
    @Mapping(target = "userId", expression = "java(refreshToken.getAuthUser().getId())")
    RefreshTokenDto toDto(RefreshToken refreshToken);
}