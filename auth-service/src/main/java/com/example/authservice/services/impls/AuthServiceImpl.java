package com.example.authservice.services.impls;

import com.example.authservice.clients.UserClient;
import com.example.authservice.clients.dtos.UserServiceCreateRequestDto;
import com.example.authservice.dtos.LoginJwtResponseDto;
import com.example.authservice.dtos.LoginRequestDto;
import com.example.authservice.dtos.UserRegisterRequestDto;
import com.example.authservice.dtos.UserRegisterResponseDto;
import com.example.authservice.entities.Role;
import com.example.authservice.entities.UserRole;
import com.example.authservice.exceptions.RoleNotFoundException;
import com.example.authservice.repositories.RoleRepository;
import com.example.authservice.repositories.UserRoleRepository;
import com.example.authservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserClient userClient;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserRegisterResponseDto register(UserRegisterRequestDto request) {
        var userCreateRequest = modelMapper.map(request, UserServiceCreateRequestDto.class);
        var userCreateResponse = userClient.create(userCreateRequest).getBody();

        Role role = roleRepository.findByName(Role.RoleName.ROLE_USER).orElseThrow(() ->
                new RoleNotFoundException("Role not found: %s".formatted(Role.RoleName.ROLE_USER)));

        assignRolesToUser(userCreateResponse.getId(), role);

        return modelMapper.map(userCreateResponse, UserRegisterResponseDto.class);
    }

    @Override
    public LoginJwtResponseDto login(LoginRequestDto request) {
        return null;
    }

    private void assignRolesToUser(String userId, Role role) {
        UserRole userRole = UserRole.builder()
                .userId(userId)
                .role(role)
                .build();

        userRoleRepository.save(userRole);
    }
}
