package ru.devvault.skilltest.service;

import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.devvault.skilltest.dto.CheckProfileRequestMessage;
import ru.devvault.skilltest.dto.MessageId;
import ru.devvault.skilltest.dto.RegistrationForm;
import ru.devvault.skilltest.entity.User;
import ru.devvault.skilltest.repository.UserRepository;

/**
 * Реализация интерфейса для работы с пользователями
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MessagingService messagingService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MessagingService messagingService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.messagingService = messagingService;
    }

    @Override
    @Transactional(TxType.SUPPORTS)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(TxType.SUPPORTS)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(TxType.SUPPORTS)
    public List<User> getUsersList() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public User createNewUser(RegistrationForm form) {
        String encodedPassword = this.passwordEncoder.encode(Arrays.toString(form.getPassword()));
        if (encodedPassword == null) {
            throw new RuntimeException("Password must not be null.");
        }
        User newUser = userRepository.save(new User(
            null,
            form.getUsername(),
            form.getFirstName(),
            form.getLastName(),
            form.getPatronymic(),
            encodedPassword,
            form.getEmail(),
            false
        ));

        MessageId messageId = messagingService.send(new CheckProfileRequestMessage(newUser));
        if (messageId == null) {
            throw new RuntimeException("Message not sent.");
        }

        return newUser;
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public void setActive(User user) {
        userRepository.setActive(true, user.getId());
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public void setInactive(User user) {
        userRepository.setActive(false, user.getId());
    }
}