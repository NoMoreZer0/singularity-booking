package com.example.singularitymanagement.DAO;

import com.example.singularitymanagement.model.Reservation;
import com.example.singularitymanagement.model.Room;
import com.example.singularitymanagement.model.Timeslot;
import com.example.singularitymanagement.service.RoomService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestReservationDAO {
    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private RoomService roomService;

    private Timeslot createTimeSlot(Long start, Long end, String purpose) {
        Timeslot timeslot = new Timeslot();
        timeslot.setStart(start);
        timeslot.setEnd(end);
        timeslot.setPurpose(purpose);
        return timeslot;
    }

    private Reservation buildNewReservation(Room room) {
        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.addTimeslot(createTimeSlot(1683650806956L, 1683650806956L, "test purpose"));
        return reservation;
    }

    @Test
    @DisplayName("save reservation test")
    public void saveReservationTest() {
        try {
            Room room303 = roomService.getByNumber("303");
            Reservation tempReservation = buildNewReservation(room303);
            reservationDAO.save(tempReservation);
            assertEquals(1, reservationDAO.count());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("find all reservations by room")
    public void findAllByRoomTest() {
        try {
            Room room303 = roomService.getByNumber("303");
            List<Reservation> reservations = reservationDAO.findAllByRoom(room303);
            assertTrue((reservations != null && !reservations.isEmpty()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("find all")
    public void findAllReservationsTest() {
        try {
            List<Reservation> reservations = (List<Reservation>) reservationDAO.findAll();
            assertFalse(reservations.isEmpty());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
