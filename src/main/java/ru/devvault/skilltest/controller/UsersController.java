package ru.devvault.skilltest.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.devvault.skilltest.entity.User;
import ru.devvault.skilltest.service.UserService;

/**
 * Контроллер управления списком зарегистрированных пользователей
 *
 * Управляет вью отображения списка зарегистрированных пользователей
 */
@Controller
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<User> list = userService.findAll();

        model.addAttribute("users", list);
        return "users";
    }
}
