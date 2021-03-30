package ru.devvault.skilltest.controller;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.devvault.skilltest.entity.User;
import ru.devvault.skilltest.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /users success")
    void testUsersSuccess() throws Exception {
        List<User> users = Collections.singletonList(new User(
            1L,
            "user1",
            "John",
            "Doe",
            "Test",
            "ENCRYPTED_TEXT",
            "john@doe.com",
            true
        ));
        doReturn(users).when(userService).findAll();

        // Execute the GET request
        mockMvc.perform(get("/users"))
            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(view().name("users"))
            .andExpect(model().attribute("users", users))
            .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))

            // Validate headers
            .andExpect(header().string(HttpHeaders.CONTENT_LANGUAGE, "en"));
    }
}
