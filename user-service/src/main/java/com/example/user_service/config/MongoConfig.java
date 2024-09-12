package com.example.user_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Configuration class for MongoDB integration.
 * <p>
 * This configuration class sets up MongoDB-specific settings for the application.
 * It enables MongoDB auditing and repository support:
 * <ul>
 *     <li>{@link EnableMongoAuditing} - Enables auditing of MongoDB documents, such as tracking creation and modification times.</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableMongoAuditing
public class MongoConfig {
}
