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
public class TestRoomController {
    @Autowired
    private WebApplicationContext applicationContext;
    @Autowired
    private RegistrationService registrationService;
    private MockMvc mockMvc;
    private String url = "/room";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("get all rooms")
    @WithMockUser(username = "test", password = "test")
    public void testSuccessfulGetAllRooms() throws Exception {
        createUser("test", "test@test.kz", "test");
        mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("successful get rooms by roomNumber")
    @WithMockUser(username = "test", password = "test")
    public void testSuccessfulGetRoomByRoomNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(url + "/303").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private void createUser(String username, String email, String password) throws UserNameOrEmailExistsException {
        registrationService.saveUser(new UserDTO(username, email, password));
    }
}
