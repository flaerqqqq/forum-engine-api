package com.example.authservice.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Kafka producer settings.
 * <p>
 * This class configures the Kafka producer settings, including the serializer,
 * bootstrap servers, and retry policy. It defines the producer factory and Kafka
 * template beans used for producing messages to Kafka topics.
 * </p>
 */
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Creates and returns Kafka producer configuration properties.
     * <p>
     * This method configures essential Kafka producer properties, including the
     * bootstrap servers, key and value serializers, acknowledgment policy, and
     * retry attempts. These properties ensure reliable message delivery to Kafka.
     * </p>
     *
     * @return a map containing Kafka producer configuration properties
     */
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        return props;
    }

    /**
     * Creates and returns a {@link ProducerFactory} bean.
     * <p>
     * The {@link ProducerFactory} is responsible for creating Kafka producer instances
     * configured with the provided properties. This factory produces producers that
     * send messages with a String key and an Object value.
     * </p>
     *
     * @return an instance of {@link ProducerFactory} with the specified configuration
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * Creates and returns a {@link KafkaTemplate} bean.
     * <p>
     * The {@link KafkaTemplate} is a high-level abstraction for sending messages to Kafka.
     * This template uses the configured producer factory to produce messages with a String key
     * and an Object value, allowing flexible message structures.
     * </p>
     *
     * @return an instance of {@link KafkaTemplate} for publishing messages to Kafka
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
