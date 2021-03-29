package ru.devvault.skilltest.validator;

import java.util.Arrays;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.devvault.skilltest.dto.RegistrationForm;
import ru.devvault.skilltest.entity.User;
import ru.devvault.skilltest.service.UserService;

@Component
public class UserValidator implements Validator {

    // common-validator library.
    private final EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private UserService userService;

    // The classes are supported by this validator.
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == RegistrationForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationForm registrationForm = (RegistrationForm) target;

        // Check the fields of registrationForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.registrationForm.username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.registrationForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.registrationForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.registrationForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmEmail", "NotEmpty.registrationForm.confirmEmail");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.registrationForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.registrationForm.confirmPassword");

        if (!this.emailValidator.isValid(registrationForm.getEmail())) {
            // Invalid email.
            errors.rejectValue("email", "Pattern.registrationForm.email");
        } else {
            User dbUser = userService.findByEmail(registrationForm.getEmail());

            if (dbUser != null) {
                // Email has been used by another account.
                errors.rejectValue("email", "Duplicate.registrationForm.email");
            }
        }

        if (!errors.hasFieldErrors("username")) {
            User dbUser = userService.findByUsername(registrationForm.getUsername());

            if (dbUser != null) {
                // Username is not available.
                errors.rejectValue("username", "Duplicate.registrationForm.username");
            }
        }

        if (!errors.hasErrors()) {
            if (!Arrays.equals(registrationForm.getConfirmPassword(), registrationForm.getPassword())) {
                errors.rejectValue("confirmPassword", "Match.registrationForm.confirmPassword");
            }
            if (!registrationForm.getConfirmEmail().equals(registrationForm.getEmail())) {
                errors.rejectValue("confirmEmail", "Match.registrationForm.confirmEmail");
            }
        }
    }

}
