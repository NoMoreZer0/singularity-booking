package com.example.singularitymanagement.controller;

import com.example.singularitymanagement.DTO.ReservationDTO;
import com.example.singularitymanagement.DTO.ReservationResponseDTO;
import com.example.singularitymanagement.DTO.TimeslotDTO;
import com.example.singularitymanagement.model.User;
import com.example.singularitymanagement.service.EmailSenderService;
import com.example.singularitymanagement.service.ReservationService;
import com.example.singularitymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private ReservationService reservationService;
    private EmailSenderService emailSenderService;

    private UserService userService;

    @Autowired
    public ReservationController(ReservationService reservationService, EmailSenderService emailSenderService, UserService userService) {
        this.reservationService = reservationService;
        this.emailSenderService = emailSenderService;
        this.userService = userService;
    }

    @GetMapping("/{roomNumber}")
    public ResponseEntity<List<ReservationResponseDTO>> getAllByRoom(@PathVariable String roomNumber) {
        List<ReservationResponseDTO> reservations;
        try {
            reservations = reservationService.getAllReservationsByRoom(roomNumber);
        } catch (Exception e) {
            reservations = new ArrayList<>();
        }
        return ResponseEntity.ok().body(reservations);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ReservationResponseDTO>> getAllMy() {
        List<ReservationResponseDTO> reservations;
        reservations = reservationService.getAllMyReservations(getUser());
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/date")
    public ResponseEntity<List<ReservationResponseDTO>> getAllByDate(@RequestBody TimeslotDTO timeslotDTO) {
        try {
            return ResponseEntity.ok().body(reservationService.getAllByDate(timeslotDTO, ""));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ArrayList<>());
        }
    }

    @PostMapping("/date/{roomNumber}")
    public <T> ResponseEntity<T> getAllRoomByDate(@PathVariable String roomNumber,
                                                                         @RequestBody TimeslotDTO timeslotDTO) {
            try {
                return (ResponseEntity<T>) ResponseEntity.ok().body(reservationService.getAllByDate(timeslotDTO, roomNumber));
            } catch (Exception e) {
                return (ResponseEntity<T>) ResponseEntity.badRequest().body(e.getMessage());
            }
    }

    @CrossOrigin
    @PatchMapping("/{roomNumber}")
    public <T> ResponseEntity<T> addReservation(@PathVariable String roomNumber,
                                                @RequestBody ReservationDTO reservationDTO) {
        if (reservationDTO.getTimeslots().isEmpty()) {
            return (ResponseEntity<T>) ResponseEntity.badRequest().body("Timeslots are empty");
        }
        try {
            reservationService.addReservation(getUser(), roomNumber, reservationDTO);
//            emailSenderService.sendEmail(getUser(), reservationDTO, roomNumber);
            return (ResponseEntity<T>) ResponseEntity.ok().body("reservations added successfully!");
        } catch (Exception e) {
            return (ResponseEntity<T>) ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @CrossOrigin
    @DeleteMapping("/{roomNumber}/{timeslotID}")
    public <T> ResponseEntity <T> deleteReservationById(@PathVariable String roomNumber,
                                                        @PathVariable Long timeslotID) {
        try {
            reservationService.deleteByTimeslotID(roomNumber, timeslotID, getUser().getId());
            return (ResponseEntity<T>) ResponseEntity.ok().body("reservation deleted successfully!");
        } catch (Exception e) {
            return (ResponseEntity<T>) ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private User getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return userService.getUserByUsername(username);
    }
}
