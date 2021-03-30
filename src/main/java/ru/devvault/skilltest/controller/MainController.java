package ru.devvault.skilltest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер заглавной (корневой) страницы
 *
 * Пока выполняет только отображение заглавной страницы
 */
@Controller
public class MainController {
    @GetMapping("/")
    public String root() {
        return "index";
    }
}
