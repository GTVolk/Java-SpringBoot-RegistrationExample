package ru.devvault.skilltest.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.devvault.skilltest.dto.EmailAddress;
import ru.devvault.skilltest.dto.EmailContent;

/**
 * Шаблонная реализация интерфейса отправки email
 *
 * В некоторых случаях может как-будто упасть по таймауту
 */
@Slf4j
@Service
public class MailerServiceImpl implements MailerService {
    @Override
    public void sendMail(EmailAddress toAddress, EmailContent messageBody) throws TimeoutException {
        if (shouldThrowTimeout()) {
            sleep();

            throw new TimeoutException("Timeout!");
        }

        if(shouldSleep()) {
            sleep();
        }

        // ok.
        log.info("Message sent to {}, body {}.", toAddress, messageBody);
    }

    @SneakyThrows
    private void sleep() {
        Thread.sleep(TimeUnit.MINUTES.toMillis(1));
    }

    private boolean shouldSleep() {
        return new Random().nextInt(10) == 1;
    }

    private boolean shouldThrowTimeout() {
        return new Random().nextInt(10) == 1;
    }
}
