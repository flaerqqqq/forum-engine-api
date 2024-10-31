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

@Component
@RequiredArgsConstructor
public class EmailConfirmEventProducer {

    @Value("${kafka.topics.email-confirm-topic}")
    private String emailConfirmTopic;

    private final AuthUserRepository authUserRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

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
