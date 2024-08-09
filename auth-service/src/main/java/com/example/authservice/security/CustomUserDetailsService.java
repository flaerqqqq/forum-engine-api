package com.example.authservice.security;

import com.example.authservice.entities.AuthUser;
import com.example.authservice.exceptions.UserNotFoundException;
import com.example.authservice.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("User with such username is not found: %s".formatted(username)));

        return new CustomUserDetails(authUser);
    }
}
