package com.example.user_service.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Custom implementation of {@link AuthenticationEntryPoint} for handling authentication errors.
 * <p>
 * This component is used to handle authentication errors by delegating to a {@link HandlerExceptionResolver}.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    /**
     * Handles authentication exceptions by resolving them with a {@link HandlerExceptionResolver}.
     *
     * @param request the {@link HttpServletRequest} that caused the exception
     * @param response the {@link HttpServletResponse} to send the response to
     * @param authException the {@link AuthenticationException} that was thrown
     * @throws IOException if an input or output error occurs
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}
