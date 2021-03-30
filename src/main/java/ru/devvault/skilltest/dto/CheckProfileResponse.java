package ru.devvault.skilltest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.devvault.skilltest.entity.User;
import ru.devvault.skilltest.enumeration.ProfileCheckStatus;

/**
 * Класс реализующий оидаемый ответ внешней системы на заявку одобрения
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckProfileResponse {
    private User user;
    private ProfileCheckStatus checkStatus;
}
