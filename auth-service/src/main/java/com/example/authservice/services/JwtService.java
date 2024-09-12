package com.example.authservice.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * Service interface for handling JSON Web Token (JWT) operations.
 * <p>
 * This interface defines methods for generating JWTs, extracting information from them,
 * and validating their validity. Implementations of this interface are responsible for
 * managing JWT-related operations in the application.
 * </p>
 */
public interface JwtService {

    /**
     * Generates a JWT based on the provided user details.
     * <p>
     * This method creates a JWT token that includes the user's details and roles.
     * </p>
     *
     * @param userDetails the {@link UserDetails} containing user information and roles
     * @return the generated JWT as a {@link String}
     */
    String generate(UserDetails userDetails);


    /**
     * Extracts the username from the given JWT token.
     * <p>
     * This method parses the JWT token and retrieves the username of the user from it.
     * </p>
     *
     * @param token the JWT token as a {@link String}
     * @return the username extracted from the token
     */
    String extractUsername(String token);

    /**
     * Extracts the roles from the given JWT token.
     * <p>
     * This method parses the JWT token and retrieves the list of roles assigned to the user.
     * </p>
     *
     * @param token the JWT token as a {@link String}
     * @return a {@link List} of {@link GrantedAuthority} representing the user's roles
     */
    List<GrantedAuthority> extractRoles(String token);

    /**
     * Validates the given JWT token.
     * <p>
     * This method checks whether the JWT token is still valid and has not expired.
     * </p>
     *
     * @param token the JWT token as a {@link String}
     * @return {@code true} if the token is valid, {@code false} otherwise
     */
    boolean isValid(String token);
}
