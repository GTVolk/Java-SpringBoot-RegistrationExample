package ru.devvault.skilltest.config;

import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для messaging решения
 *
 * Здесь в будущем можно реализовывать конфиг бинов, которые будут участвовать в обмене сообщений по шине
 */
@Configuration
public class MessagingConfig {
    public static final String ROUTING_KEY_PROFILE_CHECK = "#.check.profile";
}
