package ru.devvault.skilltest.service;

import java.util.concurrent.TimeoutException;
import ru.devvault.skilltest.dto.EmailAddress;
import ru.devvault.skilltest.dto.EmailContent;

/**
 * Интерфейс сервиса отправки email сообщений
 */
public interface MailerService {
    void sendMail(EmailAddress toAddress, EmailContent messageBody) throws TimeoutException;
}
