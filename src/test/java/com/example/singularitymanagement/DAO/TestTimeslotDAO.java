package com.example.singularitymanagement.DAO;

import com.example.singularitymanagement.model.Timeslot;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestTimeslotDAO {
    @Autowired
    private TimeslotDAO timeslotDAO;

    @Test
    @DisplayName("test get timeslt by id")
    public void getTimeSlotByIdTest() {
        Optional<Timeslot> opt = timeslotDAO.findById(1L);
        assertTrue(opt.isEmpty());
    }
}
