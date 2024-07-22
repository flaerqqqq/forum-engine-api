package com.example.authservice.services.impls;

import com.example.authservice.clients.UserClient;
import com.example.authservice.clients.dtos.UserServiceCreateRequestDto;
import com.example.authservice.dtos.UserRegisterRequestDto;
import com.example.authservice.dtos.UserRegisterResponseDto;
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

        Role role = roleRepository.findByName("ROLE_USER");

        assignRolesToUser(userCreateResponse.getId(), role.getId());

        return modelMapper.map(userCreateResponse, UserRegisterResponseDto.class);
    }

    private void assignRolesToUser(String userId, String roleId) {
        UserRole userRole = UserRole.builder()
                .userId(userId)
                .roleId(roleId)
                .build();

        userRoleRepository.save(userRole);
    }
}
