package com.example.user_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class for handling JSON Web Tokens (JWTs).
 * <p>
 * This component provides methods to extract information from JWTs, such as the username and roles. It uses a secret key
 * for parsing and validating JWTs. The secret key is configured via application properties.
 * </p>
 */
@Component
public class JwtUtils {

    private final SecretKey key;

    /**
     * Constructs a {@link JwtUtils} instance with the specified secret key.
     *
     * @param secret the secret key used for signing and verifying JWTs
     */
    public JwtUtils(@Value("${jwt.secret.key}") String secret) {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Extracts the username from the given JWT.
     *
     * @param token the JWT from which to extract the username
     * @return the extracted username
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }


    /**
     * Extracts the roles from the given JWT.
     *
     * @param token the JWT from which to extract the roles
     * @return a list of {@link GrantedAuthority} objects representing the roles
     */
    public List<GrantedAuthority> extractRoles(String token) {
        return ((List<Map<String, String>>)extractClaims(token).get("roles")).get(0).values().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }

    /**
     * Extracts the claims from the given JWT.
     *
     * @param token the JWT from which to extract the claims
     * @return the extracted {@link Claims}
     */
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
