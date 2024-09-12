package com.example.authservice.security;

import com.example.authservice.entities.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Custom implementation of {@link UserDetails} for representing user-specific details in Spring Security.
 * <p>
 * This class wraps an {@link AuthUser} entity and provides the necessary user details and authorities
 * required by Spring Security.
 * </p>
 */
public class CustomUserDetails implements UserDetails {

    private final AuthUser authUser;


    /**
     * Constructs a {@link CustomUserDetails} instance with the specified {@link AuthUser}.
     *
     * @param authUser the {@link AuthUser} entity containing user information
     */
    public CustomUserDetails(AuthUser authUser) {
        this.authUser = authUser;
    }


    /**
     * Returns the authorities granted to the user.
     * <p>
     * This method extracts roles from the {@link AuthUser} and maps them to {@link GrantedAuthority} objects.
     * </p>
     *
     * @return a collection of {@link GrantedAuthority} representing the user's roles
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authUser.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getName().toString())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password of the user
     */
    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return the username of the user
     */
    @Override
    public String getUsername() {
        return authUser.getUsername();
    }
}
