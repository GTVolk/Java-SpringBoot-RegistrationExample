package ru.devvault.skilltest.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.devvault.skilltest.dto.RegistrationForm;
import ru.devvault.skilltest.entity.User;
import ru.devvault.skilltest.repository.UserRepository;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private List<User> users;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        users.add(new User(
            1L,
            "user1",
            "John",
            "Doe",
            "Test",
            "ENCRYPTED_TEXT",
            "john@doe.com",
            true
        ));
        users.add(new User(
            2L,
            "user2",
            "Jane",
            "Doe",
            "Test",
            "ENCRYPTED_TEXT",
            "jane@doe.com",
            false
        ));
    }

    @Test
    @DisplayName("Test findAll")
    void testFindAll() {
        // Setup our mock repository
        doReturn(users).when(userRepository).findAll();

        // Execute the service call
        List<User> users = userService.getUsersList();

        // Assert the response
        Assertions.assertEquals(2, users.size(), "findAll should return 2 users");
    }

    @Test
    @DisplayName("Test findByEmail Success")
    void testFindByEmail() {
        // Setup our mock repository
        doReturn(users.get(0)).when(userRepository).findByEmail("john@doe.com");

        // Execute the service call
        User returnedUser = userService.findByEmail("john@doe.com");

        // Assert the response
        Assertions.assertNotNull(returnedUser, "User was not found");
        Assertions.assertSame(returnedUser, users.get(0), "The user returned was not the same as the mock");
    }

    @Test
    @DisplayName("Test findById Not Found")
    void testFindByEmailNotFound() {
        // Setup our mock repository
        doReturn(null).when(userRepository).findByEmail("jack@doe.com");

        // Execute the service call
        User returnedUser = userService.findByEmail("jack@doe.com");

        // Assert the response
        Assertions.assertNull(returnedUser, "User should not be found");
    }

    @Test
    @DisplayName("Test save user")
    void testSave() {
        User mockUser = users.get(0);
        RegistrationForm registrationForm = new RegistrationForm(
            mockUser.getUsername(),
            mockUser.getFirstName(),
            mockUser.getLastName(),
            mockUser.getPatronymic(),
            "password".toCharArray(),
            "password".toCharArray(),
            mockUser.getEmail(),
            mockUser.getEmail()
        );

        // Setup our mock repository
        doReturn(mockUser).when(userRepository).save(any());

        // Execute the service call
        User returnedUser = userService.createNewUser(registrationForm);

        // Assert the response
        Assertions.assertNotNull(returnedUser, "The saved user should not be null");
        Assertions.assertEquals(mockUser.getUsername(), registrationForm.getUsername());
        Assertions.assertEquals(mockUser.getEmail(), registrationForm.getEmail());
    }
}
