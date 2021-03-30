package ru.devvault.skilltest.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.devvault.skilltest.dto.CheckProfileResponse;
import ru.devvault.skilltest.dto.CheckProfileResponseMessage;
import ru.devvault.skilltest.entity.User;
import ru.devvault.skilltest.enumeration.ProfileCheckStatus;

@SpringBootTest
class ProfileCheckListenerServiceTest {
    @Autowired
    private ProfileCheckListenerService profileCheckListenerService;

    @MockBean
    private UserService userService;

    @MockBean
    private MailerService mailerService;

    private CheckProfileResponseMessage message;

    @BeforeEach
    void setUp() {
        message = new CheckProfileResponseMessage(new CheckProfileResponse(
            new User(
                1L,
                "user1",
                "John",
                "Doe",
                "Test",
                "ENCRYPTED_TEXT",
                "john@doe.com",
                false
            ),
            ProfileCheckStatus.VALID
        ));
    }

    @SneakyThrows
    @Test
    @DisplayName("Test handle message")
    void testHandleMessage() {
        Assertions.assertDoesNotThrow(() -> profileCheckListenerService.handleMessage(message));
        Mockito.verify(userService, times(1)).setActive(message.getMessageBody().getUser());
        Mockito.verify(mailerService, times(1)).sendMail(any(), any());
    }
}
