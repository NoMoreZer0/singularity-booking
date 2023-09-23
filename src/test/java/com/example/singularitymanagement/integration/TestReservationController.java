package com.example.singularitymanagement.integration;

import com.example.singularitymanagement.DTO.ReservationDTO;
import com.example.singularitymanagement.DTO.TimeslotDTO;
import com.example.singularitymanagement.DTO.UserDTO;
import com.example.singularitymanagement.exception.UserNameOrEmailExistsException;
import com.example.singularitymanagement.model.Timeslot;
import com.example.singularitymanagement.model.User;
import com.example.singularitymanagement.service.RegistrationService;
import com.example.singularitymanagement.service.ReservationService;
import com.example.singularitymanagement.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestReservationController {
    @Autowired
    private WebApplicationContext applicationContext;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;

    private MockMvc mockMvc;
    private String url = "/reservation";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("test successful add reservation")
    @WithMockUser(username = "test", password = "test")
    public void testSuccessfulAddReservation() throws Exception {
        createUser("test", "test@test.kz", "test");
        ReservationDTO reservationDTO = createReservation();
        mockMvc.perform(MockMvcRequestBuilders.patch(url + "/303").contentType(MediaType.APPLICATION_JSON).content(mapToJson(reservationDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("test conflicting reservation")
    @WithMockUser(username = "test", password = "test")
    public void testConflictingReservation() throws Exception {
        ReservationDTO reservationDTO = createReservation();
        mockMvc.perform(MockMvcRequestBuilders.patch(url + "/303").contentType(MediaType.APPLICATION_JSON).content(mapToJson(reservationDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("test same reservation for different room")
    @WithMockUser(username = "test", password = "test")
    public void testSameReservationForDifferentRoom() throws Exception {
        ReservationDTO reservationDTO = createReservation();
        mockMvc.perform(MockMvcRequestBuilders.patch(url + "/403").contentType(MediaType.APPLICATION_JSON).content(mapToJson(reservationDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("test successful reservations by room number")
    @WithMockUser(username = "test", password = "test")
    public void testSuccessfulReservationsByRoomNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(url + "/303").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("test successful reservations by user")
    @WithMockUser(username = "test", password = "test")
    public void testSuccessfulReservationByUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(url + "/my").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("test all reservations sorted by date")
    @WithMockUser(username = "test", password = "test")
    public void testAllReservationsByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(url + "/date").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("test room reservations sorted by date")
    @WithMockUser(username = "test", password = "test")
    public void testRoomReservationsByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(url + "/date/303").contentType(MediaType.APPLICATION_JSON).content(mapToJson(new TimeslotDTO(0L, 0L))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("test not existing room reservations sorted by date")
    @WithMockUser(username = "test", password = "test")
    public void testNotExistingRoomReservationByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(url + "/date/303").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("delete user reservation")
    @WithMockUser(username = "test", password = "test")
    public void testDeleteUserReservation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(url + "/303/5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private User getTestUser() {
        return userService.getUserByUsername("test");
    }

    private Timeslot createTimeSlot(Long start, Long end, String purpose) {
        Timeslot timeslot = new Timeslot();
        timeslot.setStart(start);
        timeslot.setEnd(end);
        timeslot.setPurpose(purpose);
        return timeslot;
    }

    private List<Timeslot> createTimeSlots() {
        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(createTimeSlot(1683650806956L, 1683650806956L, "test purpose"));
        return timeslots;
    }

    private ReservationDTO createReservation() {
        return new ReservationDTO(createTimeSlots());
    }

    private void createUser(String username, String email, String password) throws UserNameOrEmailExistsException {
        registrationService.saveUser(new UserDTO(username, email, password));
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
