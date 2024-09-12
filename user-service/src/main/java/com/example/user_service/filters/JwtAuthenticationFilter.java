package com.example.user_service.filters;

import com.example.user_service.exceptions.CustomAuthException;
import com.example.user_service.security.CustomAuthenticationEntryPoint;
import com.example.user_service.security.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filter that processes JWT tokens for authentication.
 * <p>
 * This filter intercepts HTTP requests, extracts JWT tokens from the Authorization header, and validates them. If the token is valid,
 * it sets the authentication in the security context. If an error occurs during processing, it delegates to the custom authentication entry point.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtils jwtUtils;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    /**
     * Processes the request to extract and validate JWT tokens.
     * <p>
     * This method is invoked for every request and performs the following steps:
     * <ul>
     *     <li>Extracts the JWT token from the Authorization header.</li>
     *     <li>Validates the token and retrieves the username and roles.</li>
     *     <li>Sets the authentication in the security context if not already set.</li>
     *     <li>Handles any exceptions by delegating to the custom authentication entry point.</li>
     * </ul>
     * </p>
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if an error occurs during processing
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader(AUTH_HEADER);

            if (header != null && header.startsWith(BEARER_PREFIX)) {
                String token = header.substring(7);

                String username = jwtUtils.extractUsername(token);
                List<GrantedAuthority> roles = jwtUtils.extractRoles(token);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, roles);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            customAuthenticationEntryPoint.commence(request, response, new CustomAuthException(ex.getMessage(), ex));
        }
    }
}
