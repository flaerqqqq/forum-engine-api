package com.example.authservice.services.impls;

import com.example.authservice.clients.UserClient;
import com.example.authservice.clients.dtos.UserServiceCreateRequestDto;
import com.example.authservice.clients.dtos.UserServiceResponseDto;
import com.example.authservice.dtos.LoginJwtResponseDto;
import com.example.authservice.dtos.LoginRequestDto;
import com.example.authservice.dtos.UserRegisterRequestDto;
import com.example.authservice.dtos.UserRegisterResponseDto;
import com.example.authservice.entities.AuthUser;
import com.example.authservice.entities.Role;
import com.example.authservice.entities.UserRole;
import com.example.authservice.exceptions.IncorrectPasswordException;
import com.example.authservice.exceptions.RoleNotFoundException;
import com.example.authservice.exceptions.UserNotFoundException;
import com.example.authservice.repositories.AuthUserRepository;
import com.example.authservice.repositories.RoleRepository;
import com.example.authservice.repositories.UserRoleRepository;
import com.example.authservice.security.CustomUserDetails;
import com.example.authservice.services.AuthService;
import com.example.authservice.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserClient userClient;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthUserRepository authUserRepository;

    @Override
    public UserRegisterResponseDto register(UserRegisterRequestDto request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        request.setUsername(hashedPassword);
        var userCreateRequest = modelMapper.map(request, UserServiceCreateRequestDto.class);
        var userCreateResponse = userClient.create(userCreateRequest).getBody();

        AuthUser authUser = createAuthUserEntity(userCreateResponse.getId(), userCreateResponse.getUsername(), hashedPassword);

        Role role = roleRepository.findByName(Role.RoleName.ROLE_USER).orElseThrow(() ->
                new RoleNotFoundException("Role not found: %s".formatted(Role.RoleName.ROLE_USER)));

        assignRolesToUser(authUser, role);

        return modelMapper.map(userCreateResponse, UserRegisterResponseDto.class);
    }

    @Override
    public LoginJwtResponseDto login(LoginRequestDto request) {
        AuthUser userResponse = authUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new UserNotFoundException("User with such username is not found: %s".formatted(request.getUsername())));

        authenticate(request);

        CustomUserDetails userDetails = new CustomUserDetails(
                request.getUsername(),
                request.getPassword(),
                userRoleRepository.findAllByUserId(userResponse.getId())
        );
        String jwtToken = jwtService.generate(userDetails);

        return new LoginJwtResponseDto(jwtToken);
    }

    private void authenticate(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(),
                loginRequestDto.getPassword()
        );

        Authentication authentication = authManager.authenticate(token);

        if (!authentication.isAuthenticated()) {
            throw new IncorrectPasswordException("Incorrect password while logging in for user with username: %s"
                    .formatted(loginRequestDto.getUsername()));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void assignRolesToUser(String userId, Role role) {
        UserRole userRole = UserRole.builder()
                .userId(userId)
                .role(role)
                .build();

        userRoleRepository.save(userRole);
    }
}
