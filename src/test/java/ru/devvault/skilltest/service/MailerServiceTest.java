package ru.devvault.skilltest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.devvault.skilltest.dto.EmailAddress;
import ru.devvault.skilltest.dto.EmailContent;

@SpringBootTest
class MailerServiceTest {
    @Autowired
    private MailerService mailerService;

    private EmailAddress emailAddress;
    private EmailContent emailContent;

    @BeforeEach
    void setUp() {
        emailAddress = new EmailAddress("john@doe.com");
        emailContent = new EmailContent("Hello, world!");
    }

    @Test
    @DisplayName("Test mail send")
    void testSendMail() {
        Assertions.assertDoesNotThrow(() -> mailerService.sendMail(emailAddress, emailContent));
    }
}
