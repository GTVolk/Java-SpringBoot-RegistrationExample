package ru.devvault.skilltest.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.devvault.skilltest.dto.RegistrationForm;
import ru.devvault.skilltest.entity.User;
import ru.devvault.skilltest.repository.UserRepository;

/**
 * Реализация интерфейса для работы с пользователями
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() { return userRepository.findAll(); }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(RegistrationForm form) {
        String encodedPassword = this.passwordEncoder.encode(Arrays.toString(form.getPassword()));
        if (encodedPassword == null) {
            throw new RuntimeException("Password must not be null.");
        }
        return userRepository.save(new User(
            null,
            form.getUsername(),
            form.getFirstName(),
            form.getLastName(),
            form.getPatronymic(),
            encodedPassword,
            form.getEmail(),
            false
        ));
    }

    @Override
    public void setActive(User user) {
        userRepository.setActive(true, user.getId());
    }

    @Override
    public void setInactive(User user) {
        userRepository.setActive(false, user.getId());
    }
}