package com.example.singularitymanagement.integration;

import com.example.singularitymanagement.DTO.UserDTO;
import com.example.singularitymanagement.exception.UserNameOrEmailExistsException;
import com.example.singularitymanagement.service.RegistrationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserController {
    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private RegistrationService registrationService;

    private MockMvc mockMvc;
    private String url = "/user";
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("get successful user information")
    @WithMockUser(username = "test", password = "test")
    public void testSuccessfulGetUserInformation() throws Exception {
        createUser("test", "test@test.kz", "test");
        mockMvc.perform(MockMvcRequestBuilders.get(url + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("get permitted user information")
    @WithMockUser(username = "test2", password = "test2")
    public void testPermittedGetUserInformation() throws Exception {
        createUser("test2", "test@test2.kz", "test2");
        mockMvc.perform(MockMvcRequestBuilders.get(url + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    private void createUser(String username, String email, String password) throws UserNameOrEmailExistsException {
        registrationService.saveUser(new UserDTO(username, email, password));
    }
}
