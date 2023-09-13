package com.example.singularitymanagement.controller;

import com.example.singularitymanagement.DAO.UserDAO;
import com.example.singularitymanagement.DTO.LoginDTO;
import com.example.singularitymanagement.DTO.UserDTO;
import com.example.singularitymanagement.exception.UserNameOrEmailExistsException;
import com.example.singularitymanagement.model.User;
import com.example.singularitymanagement.service.RegistrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestLoginController {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private RegistrationService registrationService;

    private String url = "/login";
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    @DisplayName("successful login")
    public void testSuccessfulLogin() throws Exception {
        initialSetup();

        LoginDTO loginDTO = new LoginDTO("test", "test");
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON).content(mapToJson(loginDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("incorrect password")
    public void testIncorrectPassword() throws Exception {
        LoginDTO loginDTO = new LoginDTO("test", "test2");
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(mapToJson(loginDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("incorrect login")
    public void testIncorrectLogin() throws Exception {
        LoginDTO loginDTO = new LoginDTO("test3", "test");
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(mapToJson(loginDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private void initialSetup() throws UserNameOrEmailExistsException {
        registrationService.saveUser(new UserDTO("test", "test@test.kz", "test"));
    }
    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
