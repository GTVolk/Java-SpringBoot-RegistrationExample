package ru.devvault.skilltest.service;

import java.util.List;
import ru.devvault.skilltest.dto.RegistrationForm;
import ru.devvault.skilltest.entity.User;

/**
 * Интерфейс сервиса для работы с пользователями
 */
public interface UserService {
    User findByEmail(String email);
    User findByUsername(String username);

    List<User> getUsersList();
    User createNewUser(RegistrationForm registration);

    void setActive(User user);
    void setInactive(User user);
}