package ru.devvault.skilltest.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.devvault.skilltest.dto.CheckProfileResponse;
import ru.devvault.skilltest.dto.EmailAddress;
import ru.devvault.skilltest.dto.EmailContent;
import ru.devvault.skilltest.dto.Message;
import ru.devvault.skilltest.entity.User;
import ru.devvault.skilltest.enumeration.ProfileCheckStatus;

/**
 * Класс реализующий слушателя (консьюмера) сообщений по шине по проверке профилей зарегистрированных пользователей
 *
 * В некоторых случаях может как-будто упасть по таймауту
 * При успешном получении сообщения и если заявка одобрена, то устанавливает у пользователя флаг активности и отсылает ему приветственное письмо
 */
@Component
@Slf4j
public class ProfileCheckListenerService implements MessageListener<CheckProfileResponse> {
    private final UserService userService;
    private final MailerService mailerService;

    @Autowired
    public ProfileCheckListenerService(UserService userService, MailerService mailerService) {
        this.userService = userService;
        this.mailerService = mailerService;
    }

    //@RabbitListener(queues = MessagingConfig::DEFAULT_QUEUE)
    @SneakyThrows
    @Override
    public void handleMessage(Message<CheckProfileResponse> incomingMessage) throws RuntimeException {
        if (shouldThrowException()) {
            sleep();

            throw new RuntimeException("Timeout!");
        }

        if (shouldSleep()) {
            sleep();
        }

        CheckProfileResponse payload = incomingMessage.getMessageBody();
        if (payload != null && payload.getUser() != null && payload.getCheckStatus().equals(ProfileCheckStatus.VALID)) {
            User user = payload.getUser();
            try {
                userService.setActive(user);
                mailerService.sendMail(new EmailAddress(user.getEmail()), new EmailContent("You have been successfully granted authority to use system!"));
            } catch (Exception e) {
                log.error("Cannot update user {} activity state", user);
                throw e;
            }
        }
    }

    @SneakyThrows
    private void sleep() {
        Thread.sleep(TimeUnit.MINUTES.toMillis(1));
    }

    private boolean shouldSleep() {
        return new Random().nextInt(10) == 1;
    }

    private boolean shouldThrowException() {
        return new Random().nextInt(10) == 1;
    }
}