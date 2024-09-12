package com.example.authservice.config;

import com.example.authservice.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for the authentication service.
 * <p>
 * This configuration class sets up security-related beans and configurations,
 * including the security filter chain, authentication manager, and password encoder.
 * It uses Spring Security to configure HTTP security, authentication, and authorization.
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Configures the security filter chain for HTTP requests.
     * <p>
     * This method disables CSRF protection and CORS, configures HTTP basic authentication
     * to be disabled, and allows unrestricted access to endpoints matching "api/v1/auth/**".
     * It also sets the session management policy to stateless to avoid session creation.
     * </p>
     *
     * @param http the {@link HttpSecurity} object for configuring security settings
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("api/v1/auth/**").permitAll()
                                .anyRequest().permitAll()
                )
                .sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    /**
     * Provides an {@link AuthenticationManager} bean for authentication.
     * <p>
     * This bean is used to authenticate users based on the configured security settings.
     * </p>
     *
     * @param authConf the {@link AuthenticationConfiguration} object for retrieving the manager
     * @return the configured {@link AuthenticationManager}
     * @throws Exception if an error occurs while creating the manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception {
        return authConf.getAuthenticationManager();
    }

    /**
     * Provides a {@link PasswordEncoder} bean for encoding passwords.
     * <p>
     * This bean uses {@link BCryptPasswordEncoder} to securely hash passwords.
     * </p>
     *
     * @return the configured {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides an {@link AuthenticationProvider} bean for authentication.
     * <p>
     * This bean uses {@link DaoAuthenticationProvider} with the configured password encoder
     * and user details service to authenticate users.
     * </p>
     *
     * @return the configured {@link AuthenticationProvider}
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(customUserDetailsService);
        return authProvider;
    }
}
