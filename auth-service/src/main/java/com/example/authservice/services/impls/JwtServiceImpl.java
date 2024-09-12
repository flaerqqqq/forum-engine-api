package com.example.authservice.services.impls;

import com.example.authservice.services.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link JwtService} interface for handling JSON Web Tokens (JWT).
 * <p>
 * This service is responsible for generating, parsing, and validating JWTs used for authentication.
 * </p>
 */
@Service
public class JwtServiceImpl implements JwtService {

    private final SecretKey secretKey;

    @Value("${jwt.time.expiration}")
    private long expirationTime;

    /**
     * Constructs a new {@link JwtServiceImpl} instance with the specified secret key.
     *
     * @param secret the secret key used for signing JWTs
     */
    public JwtServiceImpl(@Value("${jwt.secret}") String secret) {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a JWT token for the specified user details.
     * <p>
     * The token includes the username and roles of the user, and has an expiration time set
     * according to the {@code jwt.time.expiration} property.
     * </p>
     *
     * @param userDetails the {@link UserDetails} containing user information
     * @return the generated JWT token as a {@link String}
     */
    @Override
    public String generate(UserDetails userDetails) {
        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + expirationTime);
        return buildToken(issuedAt, expiredAt, userDetails);
    }

    /**
     * Builds a JWT token with the specified issued and expiration dates and user details.
     *
     * @param issuedAt the date when the token is issued
     * @param expiredAt the date when the token expires
     * @param userDetails the {@link UserDetails} containing user information
     * @return the JWT token as a {@link String}
     */
    private String buildToken(Date issuedAt, Date expiredAt, UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(secretKey)
                .compact();
    }


    /**
     * Extracts the username from the specified JWT token.
     *
     * @param token the JWT token as a {@link String}
     * @return the username contained in the token
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }


    /**
     * Extracts the roles from the specified JWT token.
     *
     * @param token the JWT token as a {@link String}
     * @return a {@link List} of {@link GrantedAuthority} extracted from the token
     */
    public List<GrantedAuthority> extractRoles(String token) {
        Claims claims = extractClaims(token);
        return ((List<String>)claims.get("roles")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Extracts the claims from the specified JWT token.
     *
     * @param token the JWT token as a {@link String}
     * @return the extracted {@link Claims} from the token
     */
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the specified JWT token is valid.
     * <p>
     * A token is considered valid if it has not expired and can be successfully parsed.
     * </p>
     *
     * @param token the JWT token as a {@link String}
     * @return {@code true} if the token is valid; {@code false} otherwise
     * @throws RuntimeException if the token is invalid or expired
     */
    public boolean isValid(String token) {
        try {
            Claims claims = extractClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                 | IllegalArgumentException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }



}
