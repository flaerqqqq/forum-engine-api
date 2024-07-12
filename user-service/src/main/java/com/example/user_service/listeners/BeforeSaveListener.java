package com.example.user_service.listeners;

import com.example.user_service.model.User;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public class BeforeSaveListener extends AbstractMongoEventListener<User> {

    @Override
    public void onBeforeSave(BeforeSaveEvent<User> event) {
        User user = event.getSource();

        user.setId(UUID.randomUUID().toString());
        user.setLastModifiedAt(LocalDateTime.now());
    }
}
