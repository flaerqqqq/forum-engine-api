package com.example.user_service.services.impls;

import com.example.user_service.UserServiceApplication;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto create(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername()))  {
            throw new UsernameAlreadyInUseException("User with such username already exists: %s".formatted(userDto.getUsername()));
        } else if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyInUseException(("User with such email already exists: %s".formatted(userDto.getEmail())));
        }

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);

        User user = userMapper.fromDto(userDto);
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto getById(String id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id is not found: %s".formatted(id));
        }

        User user = userRepository.findById(id).get();

        return userMapper.toDto(user);
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        Page<User> pageOfUsers = userRepository.findAll(pageable);
        return pageOfUsers.map(userMapper::toDto);
    }

    @Override
    public void delete(String id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id is not found: %s".formatted(id));
        }
        userRepository.deleteById(id);
    }
}
