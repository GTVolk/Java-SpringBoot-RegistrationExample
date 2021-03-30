package ru.devvault.skilltest.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.devvault.skilltest.constraint.FieldMatch;

/**
 * Класс реализующий форму регистрации
 *
 * Необходима для вью регистрации и валидации
 */
@Component
@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
    @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm {
    @NotNull
    private String username;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String patronymic;
    @NotNull
    private char[] password;    // Because String is staying in heap. And makes system vulnerable!
    @NotNull
    private char[] confirmPassword;     // Because String is staying in heap. And makes system vulnerable!
    @Email
    @NotNull
    private String email;
    @Email
    @NotNull
    private String confirmEmail;
}
