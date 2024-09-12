package com.example.user_service.listeners;

import com.example.user_service.model.User;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Listener for MongoDB events related to the {@link User} entity.
 * <p>
 * This component listens for events before a {@link User} entity is converted for storage in MongoDB.
 * It ensures that a unique ID is assigned to the user if one is not already set.
 * </p>
 */
@Component
public class BeforeUserConvertListener extends AbstractMongoEventListener<User> {

    /**
     * Event handler that is triggered before a {@link User} entity is converted to MongoDB format.
     * <p>
     * This method assigns a new UUID to the {@link User} if the ID is not already set. It also marks the user as new.
     * </p>
     *
     * @param event the event containing the {@link User} entity being converted
     */
    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        User user = event.getSource();

        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
            user.setNew(true);
        }
    }
}
