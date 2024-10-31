package com.example.authservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Enables scheduling in non-test environments.
 * <p>
 * This configuration activates scheduled tasks via {@link EnableScheduling}
 * when the "test" profile is not active. It prevents scheduled tasks
 * from running during tests, ensuring isolated test execution.
 * </p>
 */
@Configuration
@Profile({"!test"})
@EnableScheduling
public class SchedulingConfig {
}
