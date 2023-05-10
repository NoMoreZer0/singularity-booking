package com.example.singularitymanagement.service;

import com.example.singularitymanagement.DAO.TimeslotDAO;
import com.example.singularitymanagement.exception.TimeslotNotFoundException;
import com.example.singularitymanagement.model.Timeslot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TimeslotService {
    private TimeslotDAO timeslotDAO;

    @Autowired
    public TimeslotService(TimeslotDAO timeslotDAO) {
        this.timeslotDAO = timeslotDAO;
    }

    public Timeslot getByTimeslotID(Long timeslotID) throws Exception{
        Optional<Timeslot> optional = timeslotDAO.findById(timeslotID);
        if (optional.isEmpty()) throw new TimeslotNotFoundException("timeslot not found!");
        return optional.get();
    }
}
