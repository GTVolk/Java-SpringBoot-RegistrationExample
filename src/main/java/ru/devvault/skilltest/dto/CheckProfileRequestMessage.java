package ru.devvault.skilltest.dto;

import ru.devvault.skilltest.config.MessagingConfig;
import ru.devvault.skilltest.entity.User;

public class CheckProfileRequestMessage extends Message<User> {
    public CheckProfileRequestMessage(User messageBody) {
        super(messageBody, MessagingConfig.ROUTING_KEY_PROFILE_CHECK);
    }
}
