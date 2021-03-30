package ru.devvault.skilltest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class MainApplicationTests {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void whenSpringContextIsBootstrapped_thenNoExceptions() {
        Assertions.assertNotNull(applicationContext.getId());
    }
}
