package com.example.authservice.security;

import com.example.authservice.entities.AuthUser;
import com.example.authservice.exceptions.UserNotFoundException;
import com.example.authservice.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class for loading user-specific data in Spring Security.
 * <p>
 * Implements {@link UserDetailsService} to retrieve user details from the repository
 * and provide them to Spring Security for authentication and authorization purposes.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    /**
     * Loads user details by username.
     * <p>
     * This method retrieves an {@link AuthUser} from the repository using the provided username.
     * If the user is not found, it throws a {@link UsernameNotFoundException}.
     * </p>
     *
     * @param username the username of the user to retrieve
     * @return a {@link UserDetails} object representing the user
     * @throws UsernameNotFoundException if the user with the specified username is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("User with such username is not found: %s".formatted(username)));

        return new CustomUserDetails(authUser);
    }
}
