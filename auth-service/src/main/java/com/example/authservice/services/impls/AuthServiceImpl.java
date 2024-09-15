package com.example.authservice.services.impls;

import com.example.authservice.clients.UserClient;
import com.example.authservice.dtos.*;
import com.example.authservice.entities.RefreshToken;
import com.example.authservice.exceptions.InvalidRefreshTokenException;
import com.example.authservice.repositories.RefreshTokenRepository;
import com.example.authservice.request.UserServiceCreateRequestDto;
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
import com.example.authservice.security.CustomUserDetailsService;
import com.example.authservice.services.AuthService;
import com.example.authservice.services.JwtService;
import com.example.authservice.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link AuthService} interface for handling authentication and user registration.
 * <p>
 * This service provides methods for user registration, login, and role assignment.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserClient userClient;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthUserRepository authUserRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;


    /**
     * Registers a new user by creating a user entity and assigning a default role.
     * <p>
     * The password is encoded before being saved, and roles are assigned based on default configuration.
     * </p>
     *
     * @param request the {@link UserRegisterRequestDto} containing user registration details
     * @return a {@link UserRegisterResponseDto} with user registration details
     */
    @Override
    public UserRegisterResponseDto register(UserRegisterRequestDto request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(hashedPassword);
        var userCreateRequest = modelMapper.map(request, UserServiceCreateRequestDto.class);
        var userCreateResponse = userClient.create(userCreateRequest).getBody();

        AuthUser authUser = createAuthUserEntity(userCreateResponse.getId(), userCreateResponse.getUsername(), hashedPassword);

        Role role = roleRepository.findByName(Role.RoleName.ROLE_USER).orElseThrow(() ->
                new RoleNotFoundException("Role not found: %s".formatted(Role.RoleName.ROLE_USER)));

        assignRolesToUser(authUser, role);

        return modelMapper.map(userCreateResponse, UserRegisterResponseDto.class);
    }

    /**
     * Authenticates a user and generates a JWT tokens upon successful login.
     * <p>
     * The method retrieves user details, performs authentication, and generates a JWT tokens for authorized users.
     * </p>
     *
     * @param request the {@link LoginRequestDto} containing user login credentials
     * @return a {@link LoginJwtResponseDto} containing the generated JWT tokens
     * @throws UserNotFoundException if the user with the specified username is not found
     * @throws IncorrectPasswordException if the authentication fails due to incorrect password
     */
    @Override
    public LoginJwtResponseDto login(LoginRequestDto request) {
        AuthUser authUser = authUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new UserNotFoundException("User with such username is not found: %s".formatted(request.getUsername())));

        authenticate(request);

        CustomUserDetails userDetails = new CustomUserDetails(authUser);
        String jwtToken = jwtService.generate(userDetails);
        RefreshTokenDto refreshTokenDto = refreshTokenService.generateRefreshToken(authUser.getId());

        return LoginJwtResponseDto.builder()
                .token(jwtToken)
                .refreshToken(refreshTokenDto.getToken())
                .build();
    }

    @Override
    public LoginJwtResponseDto refresh(String refreshToken) {
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken).orElseThrow(() ->
                new InvalidRefreshTokenException("Such token is not found in database: %s".formatted(refreshToken)));

        AuthUser authUser = refreshTokenEntity.getAuthUser();

        String jwtToken = jwtService.generate(new CustomUserDetails(authUser));
        String newRefreshToken = refreshTokenService.generateRefreshToken(authUser.getId()).getToken();

        return LoginJwtResponseDto.builder()
                .token(jwtToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    /**
     * Authenticates the user based on the provided login credentials.
     * <p>
     * This method performs authentication using the {@link AuthenticationManager} and sets the authentication
     * context in the {@link SecurityContextHolder}.
     * </p>
     *
     * @param loginRequestDto the {@link LoginRequestDto} containing login credentials
     * @throws IncorrectPasswordException if authentication fails
     */
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

    /**
     * Assigns a role to a user by creating a {@link UserRole} entity and saving it.
     *
     * @param authUser the {@link AuthUser} entity to which the role will be assigned
     * @param role the {@link Role} to be assigned to the user
     */
    private void assignRolesToUser(AuthUser authUser, Role role) {
        UserRole userRole = UserRole.builder()
                .authUser(authUser)
                .role(role)
                .build();

        userRoleRepository.save(userRole);
    }

    /**
     * Creates and saves a new {@link AuthUser} entity with the provided details.
     *
     * @param id the ID of the user
     * @param username the username of the user
     * @param password the encoded password of the user
     * @return the created {@link AuthUser} entity
     */
    private AuthUser createAuthUserEntity(String id,String username, String password) {
        AuthUser authUser = AuthUser.builder()
                .id(id)
                .username(username)
                .password(password)
                .build();

        return authUserRepository.save(authUser);
    }
}
