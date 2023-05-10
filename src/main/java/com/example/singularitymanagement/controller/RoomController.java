package com.example.singularitymanagement.controller;

import com.example.singularitymanagement.DTO.RoomDTO;
import com.example.singularitymanagement.model.Room;
import com.example.singularitymanagement.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<String> saveRoom(@RequestBody RoomDTO roomDTO) {
        roomService.saveRoom(roomDTO);
        return ResponseEntity.ok().body("room saved successfully!");
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok().body(roomService.getAll());
    }

    @GetMapping("/{roomNumber}")
    public <T> ResponseEntity<T> getRoom(@PathVariable String roomNumber) {
        try {
            return (ResponseEntity<T>) ResponseEntity.ok().body(roomService.getByNumber(roomNumber));
        } catch (Exception e) {
            return (ResponseEntity<T>) ResponseEntity.status(HttpStatus.NOT_FOUND).body("room not found");
        }
    }
}
