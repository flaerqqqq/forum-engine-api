package com.example.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * Kafka topic configuration.
 * <p>
 * Configures Kafka topics for the application. Creates the `email-confirm-topic`
 * with specific partition and replica settings upon application startup.
 * </p>
 */
@Configuration
public class KafkaTopicsConfig {

    /**
     * Defines Kafka topics with custom configurations.
     * <p>
     * The `email-confirm-topic` is set up with 3 partitions and 1 replica.
     * </p>
     *
     * @return a {@link KafkaAdmin.NewTopics} instance defining the topics.
     */
    @Bean
    public KafkaAdmin.NewTopics kafkaTopics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("email-confirm-topic")
                        .partitions(3)
                        .replicas(1)
                        .build()
        );
    }
}
