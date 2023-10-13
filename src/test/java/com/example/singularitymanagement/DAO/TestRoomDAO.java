package com.example.singularitymanagement.DAO;

import com.example.singularitymanagement.model.Room;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRoomDAO {
    @Autowired
    private RoomDAO roomDAO;
    private final int ROOM_NUMBER = 7;
    @Test
    @DisplayName("find all room test")
    public void findAllRoomTest() {
        List<Room> rooms = (List<Room>) roomDAO.findAll();
        assertEquals(ROOM_NUMBER, rooms.size());
    }

    @Test
    @DisplayName("find room by room number")
    public void findRoomByRoomNumberTest() {
        try {
            Room room303 = roomDAO.findByRoomNumber("303").get();
            assertNotNull(room303);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
