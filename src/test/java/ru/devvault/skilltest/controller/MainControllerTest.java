package ru.devvault.skilltest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET / success")
    void testRootSuccess() throws Exception {
        // Execute the GET request
        mockMvc.perform(get("/"))
            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))

            // Validate headers
            .andExpect(header().string(HttpHeaders.CONTENT_LANGUAGE, "en"));
    }
}
