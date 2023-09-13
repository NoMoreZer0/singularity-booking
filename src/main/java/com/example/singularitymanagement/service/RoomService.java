package com.example.singularitymanagement.service;

import com.example.singularitymanagement.DAO.RoomDAO;
import com.example.singularitymanagement.DTO.RoomDTO;
import com.example.singularitymanagement.exception.RoomNotFoundException;
import com.example.singularitymanagement.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private RoomDAO roomDAO;

    @Autowired
    public RoomService(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    public List<Room> getAll() {
        return (List<Room>) roomDAO.findAll();
    }

    public Room getByNumber(String roomNumber) throws RoomNotFoundException {
        Optional<Room> room = roomDAO.findByRoomNumber(roomNumber);
        if (room.isEmpty()) {
            throw new RoomNotFoundException("room not found!");
        }
        return room.get();
    }

}
