package ru.devvault.skilltest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.devvault.skilltest.dto.CheckProfileRequestMessage;
import ru.devvault.skilltest.entity.User;

@SpringBootTest
class MessagingServiceTest {
    @Autowired
    private MessagingService messagingService;

    private CheckProfileRequestMessage message;

    @BeforeEach
    void setUp() {
        message = new CheckProfileRequestMessage(new User(
            1L,
            "user1",
            "John",
            "Doe",
            "Test",
            "ENCRYPTED_TEXT",
            "john@doe.com",
            false
        ));
    }

    @Test
    @DisplayName("Test send")
    void testSend() {
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertNotNull(messagingService.send(message));
        });
    }

    @Test
    @DisplayName("Test receive")
    void testReceive() {
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertNull(messagingService.receive(message.getMessageId()));
        });
    }

    @Test
    @DisplayName("Test do request")
    void testDoRequest() {
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertNull(messagingService.doRequest(message));
        });
    }
}
