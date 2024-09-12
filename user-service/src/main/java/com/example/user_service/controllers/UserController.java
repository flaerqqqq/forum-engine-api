package com.example.user_service.controllers;

import com.example.user_service.dto.UserCreateRequestDto;
import com.example.user_service.dto.UserDto;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.mappers.UserMapper;
import com.example.user_service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing users.
 * <p>
 * Provides endpoints for creating, retrieving, listing, and deleting user records.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Creates a new user.
     * <p>
     * This endpoint accepts a {@link UserCreateRequestDto} object to create a new user and returns
     * a {@link UserResponseDto} with the details of the created user.
     * </p>
     *
     * @param request the user creation request
     * @return a {@link ResponseEntity} containing the created user details and HTTP status code
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateRequestDto request) {
        UserDto requestDto = userMapper.toDto(request);
        UserDto response = userService.create(requestDto);
        return new ResponseEntity<>(userMapper.toResponseDto(response), HttpStatus.CREATED);
    }


    /**
     * Retrieves a user by ID.
     * <p>
     * This endpoint retrieves a user by its ID and returns a {@link UserResponseDto} with the user's details.
     * </p>
     *
     * @param id the ID of the user to retrieve
     * @return a {@link ResponseEntity} containing the user details and HTTP status code
     */
    @GetMapping("{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable String id) {
        UserDto user = userService.getById(id);
        return new ResponseEntity<>(userMapper.toResponseDto(user), HttpStatus.OK);
    }

    /**
     * Retrieves a paginated list of users.
     * <p>
     * This endpoint returns a paginated list of users based on the provided {@link Pageable} object.
     * </p>
     *
     * @param pageable the pagination information
     * @return a {@link ResponseEntity} containing a page of user details or a no content response
     */
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAll(Pageable pageable) {
        Page<UserDto> pageOfUsers = userService.getAll(pageable);
        if (pageOfUsers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(pageOfUsers.map(userMapper::toResponseDto), HttpStatus.OK);
    }


    /**
     * Deletes a user by ID.
     * <p>
     * This endpoint deletes a user identified by its ID and returns a no content response.
     * </p>
     *
     * @param id the ID of the user to delete
     * @return a {@link ResponseEntity} with a no content response
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
