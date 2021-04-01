package ru.devvault.skilltest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.devvault.skilltest.dto.MessageId;
import ru.devvault.skilltest.dto.RegistrationForm;
import ru.devvault.skilltest.entity.User;
import ru.devvault.skilltest.service.MessagingService;
import ru.devvault.skilltest.service.UserService;
import ru.devvault.skilltest.validator.UserValidator;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {
    @MockBean
    private UserService userService;

    @MockBean
    private UserValidator userValidator;

    @MockBean
    private MessagingService messagingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /register success")
    void testRegisterSuccess() throws Exception {
        doReturn(true).when(userValidator).supports(RegistrationForm.class);

        // Execute the GET request
        mockMvc.perform(get("/register"))
            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(view().name("register"))
            .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
            .andExpect(model().attributeExists("registrationForm"))

            // Validate headers
            .andExpect(header().string(HttpHeaders.CONTENT_LANGUAGE, "en"));
    }

    @Test
    @DisplayName("POST /register success")
    void testRegisterUserAccountSuccess() throws Exception {
        User mockUser = new User(
            1L,
            "user1",
            "John",
            "Doe",
            "Test",
            "ENCRYPTED_PASSWORD",
            "john@doe.com",
            false
        );
        RegistrationForm registrationForm = new RegistrationForm(
            "user1",
            "John",
            "Doe",
            "Test",
            "password".toCharArray(),
            "password".toCharArray(),
            "john@doe.com",
            "john@doe.com"
        );

        doReturn(mockUser).when(userService).createNewUser(any());
        doReturn(new MessageId()).when(messagingService).send(any());
        doReturn(true).when(userValidator).supports(RegistrationForm.class);

        // Execute the GET request
        mockMvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .content((new ObjectMapper()).writeValueAsBytes(registrationForm)))

            // Validate the response code and content type
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/register/success"))
            .andExpect(view().name("redirect:/register/success"))
            .andExpect(model().hasNoErrors())
            .andExpect(flash().attribute("newUser", mockUser))

            // Validate headers
            .andExpect(header().string(HttpHeaders.LOCATION, "/register/success"))
            .andExpect(header().string(HttpHeaders.CONTENT_LANGUAGE, "en"));
    }

}
