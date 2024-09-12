package com.example.user_service.services;

import com.example.user_service.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing user-related operations.
 * <p>
 * This interface defines methods for creating, retrieving, and deleting users. Implementations of this interface handle
 * the actual business logic and data interaction.
 * </p>
 */
public interface UserService {

    /**
     * Creates a new user based on the provided {@link UserDto}.
     *
     * @param userDto the {@link UserDto} containing user details
     * @return the created {@link UserDto}
     */
    UserDto create(UserDto userDto);

    /**
     * Retrieves a user by its ID.
     *
     * @param id the ID of the user to retrieve
     * @return the {@link UserDto} of the retrieved user
     */
    UserDto getById(String id);

    /**
     * Retrieves all users with pagination support.
     *
     * @param pageable the pagination information
     * @return a {@link Page} of {@link UserDto} objects
     */
    Page<UserDto> getAll(Pageable pageable);

    /**
     * Deletes a user by its ID.
     *
     * @param id the ID of the user to delete
     */
    void delete(String id);
}
