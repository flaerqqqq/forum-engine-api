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

@Service
public class JwtServiceImpl implements JwtService {

    private final SecretKey secretKey;

    @Value("${jwt.time.expiration}")
    private long expirationTime;

    public JwtServiceImpl(@Value("${jwt.secret}") String secret) {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generate(UserDetails userDetails) {
        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + expirationTime);
        return buildToken(issuedAt, expiredAt, userDetails);
    }

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

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public List<GrantedAuthority> extractRoles(String token) {
        Claims claims = extractClaims(token);
        return ((List<String>)claims.get("roles")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

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
