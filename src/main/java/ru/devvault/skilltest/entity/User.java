package ru.devvault.skilltest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс отображающий сущность пользователя из таблицы БД
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "st_users", uniqueConstraints = {@UniqueConstraint(columnNames = "username"),@UniqueConstraint(columnNames = "email")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;    // Record id

    @Column(name = "username", nullable = false, unique = true)
    @NotEmpty(message = "Please provide your user name")
    private String username;

    @Column(name = "first_name", nullable = false)
    @NotEmpty(message = "Please provide your first name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotEmpty(message = "Please provide your last name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Please provide password")
    private String encryptedPassword;   // Encrypted using bcrypt password

    @Email(message = "Please provide a valid e-mail")
    @NotEmpty(message = "Please provide an e-mail")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;   // User profile is activated and accepted from external check system

    @Override
    public String toString() {
        return "User{" +
            " id='" + id + "'" +
            ", username='" + getUsername() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", patronymic='" + getPatronymic() + "'" +
            ", email='" + getEmail() + "'" +
            ", is_active='" + getIsActive() + "'" +
            "}";
    }
}
