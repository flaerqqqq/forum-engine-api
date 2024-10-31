package com.example.authservice.producers;

import com.example.authservice.entities.EmailConfirmEvent;
import com.example.authservice.exceptions.UserNotFoundException;
import com.example.authservice.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Producer for sending email confirmation events to Kafka.
 * <p>
 * This class is responsible for creating and sending email confirmation events to the specified Kafka topic.
 * It verifies the existence of the user in the repository before attempting to send the event.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class EmailConfirmEventProducer {

    /**
     * The name of the Kafka topic for email confirmation events.
     */
    @Value("${kafka.topics.email-confirm-topic}")
    private String emailConfirmTopic;

    private final AuthUserRepository authUserRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Sends an email confirmation event to Kafka.
     * <p>
     * This method checks if the user with the given UUID exists in the repository. If the user is not found,
     * it throws a {@link UserNotFoundException}. If the user exists, it builds an {@link EmailConfirmEvent}
     * and sends it to the configured Kafka topic asynchronously, returning a CompletableFuture for tracking the result.
     * </p>
     *
     * @param uuid the unique identifier of the user.
     * @param email the email address to which the confirmation is sent.
     * @return a CompletableFuture containing the result of the send operation.
     * @throws UserNotFoundException if the user with the specified UUID does not exist.
     */
    public CompletableFuture<SendResult<String, Object>> sendEmailConfirmation(String uuid, String email) {
        if (!authUserRepository.existsById(uuid)) {
            throw new UserNotFoundException("User with such uuid is not found: %s".formatted(uuid));
        }

        EmailConfirmEvent emailConfirmEvent = EmailConfirmEvent.builder()
                .uuid(uuid)
                .email(email)
                .build();

        return kafkaTemplate.send(emailConfirmTopic, emailConfirmEvent);
    }
}
