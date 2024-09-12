package com.example.user_service.services.impls;

import com.example.user_service.dto.UserDto;
import com.example.user_service.exceptions.EmailAlreadyInUseException;
import com.example.user_service.exceptions.UserNotFoundException;
import com.example.user_service.exceptions.UsernameAlreadyInUseException;
import com.example.user_service.mappers.UserMapper;
import com.example.user_service.model.User;
import com.example.user_service.repositories.UserRepository;
import com.example.user_service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserService} interface.
 * <p>
 * This service handles user-related operations such as creating, retrieving, and deleting users. It uses a repository for
 * interacting with the data store and a mapper for converting between DTOs and the User model.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Creates a new user based on the provided {@link UserDto}.
     *
     * @param userDto the {@link UserDto} containing user details
     * @return the created {@link UserDto}
     * @throws UsernameAlreadyInUseException if a user with the same username already exists
     * @throws EmailAlreadyInUseException if a user with the same email already exists
     */
    @Override
    public UserDto create(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername()))  {
            throw new UsernameAlreadyInUseException("User with such username already exists: %s".formatted(userDto.getUsername()));
        } else if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyInUseException(("User with such email already exists: %s".formatted(userDto.getEmail())));
        }

        User user = userMapper.fromDto(userDto);
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param id the ID of the user to retrieve
     * @return the {@link UserDto} of the retrieved user
     * @throws UserNotFoundException if no user is found with the given ID
     */
    @Override
    public UserDto getById(String id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id is not found: %s".formatted(id));
        }

        User user = userRepository.findById(id).get();

        return userMapper.toDto(user);
    }

    /**
     * Retrieves all users with pagination.
     *
     * @param pageable the pagination information
     * @return a {@link Page} of {@link UserDto} objects
     */
    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        Page<User> pageOfUsers = userRepository.findAll(pageable);
        return pageOfUsers.map(userMapper::toDto);
    }


    /**
     * Deletes a user by its ID.
     *
     * @param id the ID of the user to delete
     * @throws UserNotFoundException if no user is found with the given ID
     */
    @Override
    public void delete(String id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id is not found: %s".formatted(id));
        }
        userRepository.deleteById(id);
    }
}
