package ru.devvault.skilltest.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Конфигурация Spring Security
 *
 * Здесь настраиваем бин конвертации паролей
 * И отключаем любые проверки доступа на веб-сервере, а так же отключаем csrf токен и окно авторизации
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Iam disabled the default Spring Boot security!!!
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/**").anyRequest();
    }

    // Iam disabled the default Spring Boot security!!!
    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        // disabled crsf and any security
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().permitAll();
    }
}
