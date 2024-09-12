package com.example.user_service.mappers;

import com.example.user_service.config.MapperConfig;
import com.example.user_service.dto.UserCreateRequestDto;
import com.example.user_service.dto.UserDto;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.model.User;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between different User-related data transfer objects (DTOs) and the User model.
 * <p>
 * This interface uses MapStruct for automatic mapping between DTOs and the User model. It provides methods to convert
 * between:
 * <ul>
 *     <li>{@link UserCreateRequestDto} and {@link UserDto}</li>
 *     <li>{@link User} and {@link UserDto}</li>
 *     <li>{@link UserDto} and {@link UserResponseDto}</li>
 * </ul>
 * The mapping configuration is provided by {@link MapperConfig}.
 * </p>
 */
@Mapper(config = MapperConfig.class)
public interface UserMapper {

    /**
     * Converts a {@link UserCreateRequestDto} to a {@link UserDto}.
     *
     * @param request the {@link UserCreateRequestDto} to convert
     * @return the resulting {@link UserDto}
     */
    UserDto toDto(UserCreateRequestDto request);

    /**
     * Converts a {@link User} to a {@link UserDto}.
     *
     * @param user the {@link User} to convert
     * @return the resulting {@link UserDto}
     */
    UserDto toDto(User user);

    /**
     * Converts a {@link UserDto} to a {@link User}.
     *
     * @param userDto the {@link UserDto} to convert
     * @return the resulting {@link User}
     */
    User fromDto(UserDto userDto);


    /**
     * Converts a {@link UserDto} to a {@link UserResponseDto}.
     *
     * @param userDto the {@link UserDto} to convert
     * @return the resulting {@link UserResponseDto}
     */
    UserResponseDto toResponseDto(UserDto userDto);
}
