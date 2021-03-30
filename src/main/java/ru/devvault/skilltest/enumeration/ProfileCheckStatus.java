package ru.devvault.skilltest.enumeration;

import lombok.AllArgsConstructor;

/**
 * Перечисление реализующее результат проверки внешней системой профиля пользователя
 */
@AllArgsConstructor
public enum ProfileCheckStatus {

    VALID("valid", "Profile passed verification"),
    INVALID("invalid", "Profile failed verification");

    private final String key;
    private final String statusName;
}
