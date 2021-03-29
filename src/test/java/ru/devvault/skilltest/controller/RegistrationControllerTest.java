package ru.devvault.skilltest.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.devvault.skilltest.entity.User;
import ru.devvault.skilltest.service.UserService;

@WebMvcTest(value = RegistrationController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class RegistrationControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void whenPostUser_thenCreateUser() throws Exception {
        User alex = new User();
        given(userService.save(Mockito.any())).willReturn(alex);

        //mvc.perform(post("/register").contentType(MediaType.APPLICATION_FORM_URLENCODED).content(alex.toString()).andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("alex")));
        //verify(userService, VerificationModeFactory.times(1)).save(Mockito.any());
        //reset(userService);
    }

}
