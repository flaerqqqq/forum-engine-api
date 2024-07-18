package com.example.user_service.listeners;

import com.example.user_service.model.User;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BeforeUserConvertListener extends AbstractMongoEventListener<User> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        User user = event.getSource();

        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
            user.setNew(true);
        }
    }
}
