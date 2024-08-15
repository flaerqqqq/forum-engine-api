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

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtils jwtUtils;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

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
