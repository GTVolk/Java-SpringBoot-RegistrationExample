package ru.devvault.skilltest.validator;

import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ru.devvault.skilltest.dto.RegistrationForm;
import ru.devvault.skilltest.entity.User;
import ru.devvault.skilltest.service.UserService;

@SpringBootTest
class UserValidatorTest {
    @Autowired
    private UserValidator userValidator;

    @MockBean
    private UserService userService;

    private RegistrationForm validRegistrationForm;
    private RegistrationForm invalidRegistrationForm;

    private Errors validErrors;
    private Errors invalidErrors;

    @BeforeEach
    void setUp() {
        doReturn(null).when(userService).findByEmail("email@devvault.ru");
        doReturn(null).when(userService).findByUsername("username");

        validRegistrationForm = new RegistrationForm();
        validRegistrationForm.setUsername("username");
        validRegistrationForm.setFirstName("John");
        validRegistrationForm.setLastName("Doe");
        validRegistrationForm.setPatronymic("Test");
        validRegistrationForm.setPassword("password".toCharArray());
        validRegistrationForm.setConfirmPassword("password".toCharArray());
        validRegistrationForm.setEmail("john@doe.com");
        validRegistrationForm.setConfirmEmail("john@doe.com");
        validErrors = new BeanPropertyBindingResult(validRegistrationForm, "registrationForm");

        invalidRegistrationForm = new RegistrationForm();
        invalidRegistrationForm.setUsername("");    // Empty username
        invalidRegistrationForm.setFirstName("");   // Empty name
        invalidRegistrationForm.setLastName("");    // Empty family
        invalidRegistrationForm.setPatronymic("Test");  // Does not matter
        invalidRegistrationForm.setPassword("".toCharArray());  // empty password
        invalidRegistrationForm.setConfirmPassword("pass".toCharArray());    // does not match
        invalidRegistrationForm.setEmail("email");  // not an email
        invalidRegistrationForm.setConfirmEmail("");   // confirm is empty
        invalidErrors = new BeanPropertyBindingResult(invalidRegistrationForm, "registrationForm");
    }

    @Test
    @DisplayName("Test supports")
    void testSupports() {
        Assertions.assertTrue(userValidator.supports(RegistrationForm.class));
        Assertions.assertFalse(userValidator.supports(User.class));
    }

    @Test
    @DisplayName("Test validate success")
    void testValidateSuccess() {
        userValidator.validate(validRegistrationForm, validErrors);
        Assertions.assertFalse(validErrors.hasErrors());
    }

    @Test
    @DisplayName("Test validate fail")
    void testValidateFailed() {
        userValidator.validate(invalidRegistrationForm, invalidErrors);
        Assertions.assertTrue(invalidErrors.hasErrors());
        Assertions.assertEquals(6, invalidErrors.getErrorCount());
    }
}
