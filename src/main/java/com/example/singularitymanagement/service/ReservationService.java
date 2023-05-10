package com.example.singularitymanagement.service;

import com.example.singularitymanagement.DAO.ReservationDAO;
import com.example.singularitymanagement.DAO.TimeslotDAO;
import com.example.singularitymanagement.DTO.ReservationDTO;
import com.example.singularitymanagement.DTO.ReservationResponseDTO;
import com.example.singularitymanagement.DTO.TimeslotDTO;
import com.example.singularitymanagement.exception.NoPermissionException;
import com.example.singularitymanagement.exception.TimeslotConflictException;
import com.example.singularitymanagement.model.Reservation;
import com.example.singularitymanagement.model.Room;
import com.example.singularitymanagement.model.Timeslot;
import com.example.singularitymanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;

@Service
public class ReservationService {
    private ReservationDAO reservationDAO;
    private UserService userService;
    private RoomService roomService;
    private TimeslotService timeslotService;

    @Autowired
    public ReservationService(ReservationDAO reservationDAO, UserService userService, RoomService roomService, TimeslotService timeslotService) {
        this.reservationDAO = reservationDAO;
        this.userService = userService;
        this.roomService = roomService;
        this.timeslotService = timeslotService;
    }

    public void addReservation(User user, String roomNumber, ReservationDTO reservationDTO) throws Exception {
        List<Reservation> reservations = reservationDAO.findAllByRoom(roomService.getByNumber(roomNumber));
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                if (isConflict(reservationDTO, reservation.getTimeslots())) {
                    throw new TimeslotConflictException("timeslots conflicting with each other!");
                }
                addTimeSlots(reservation, reservationDTO.getTimeslots(), user);
                reservationDAO.save(reservation);
            }
        } else {
            Reservation reservation = new Reservation(reservationDTO, user);
            reservation.setRoom(roomService.getByNumber(roomNumber));
            reservationDAO.save(reservation);
        }
    }

    public List<ReservationResponseDTO> getAllReservationsByRoom(String roomNumber) throws Exception {
        Room room = roomService.getByNumber(roomNumber);
        List<Reservation> reservations = reservationDAO.findAllByRoom(room);
        List<ReservationResponseDTO> reservationResponseDTOS = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationResponseDTOS.add(new ReservationResponseDTO(reservation));
        }
        return reservationResponseDTOS;
    }

    public List<ReservationResponseDTO> getAllMyReservations(User user) {
            List<Reservation> reservations = (List<Reservation>) reservationDAO.findAll();
            List<ReservationResponseDTO> reservationResponseDTOS = new ArrayList<>();
            for (Reservation reservation : reservations) {
                ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO(reservation);
                reservationResponseDTO.setTimeslots(new ArrayList<>());
                for (Timeslot timeslot : reservation.getTimeslots()) {
                    if (timeslot.getUser().equals(user)) {
                        reservationResponseDTO.getTimeslots().add(new TimeslotDTO(timeslot));
                    }
                }
                if (reservationResponseDTO.getTimeslots().isEmpty()) continue;
                Collections.sort(reservationResponseDTO.getTimeslots());
                reservationResponseDTOS.add(reservationResponseDTO);
            }
            return reservationResponseDTOS;
    }

    public List<ReservationResponseDTO> getAllByDate(TimeslotDTO timeslotDTO, String roomNumber) throws Exception {
        List<ReservationResponseDTO> reservationResponseDTOS = new ArrayList<>();
        List<Reservation> reservations;
        if (roomNumber.equals("")) {
            reservations = (List<Reservation>) reservationDAO.findAll();
        } else {
            reservations = reservationDAO.findAllByRoom(roomService.getByNumber(roomNumber));
        }
        for (Reservation reservation : reservations) {
            ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO(reservation);
            reservationResponseDTO.setTimeslots(new ArrayList<>());
            for (Timeslot timeslot : reservation.getTimeslots()) {
                if (isInside(timeslot.getStart(), timeslot.getEnd(), timeslotDTO.getStart(), timeslotDTO.getEnd())) {
                    reservationResponseDTO.getTimeslots().add(new TimeslotDTO(timeslot));
                }
            }
            if (!reservationResponseDTO.getTimeslots().isEmpty()) {
                Collections.sort(reservationResponseDTO.getTimeslots());
                reservationResponseDTOS.add(reservationResponseDTO);
            }
        }
        return reservationResponseDTOS;
    }
    public void deleteByTimeslotID(String roomNumber, Long timeslotID, Long userID) throws Exception {
        List<Reservation> reservations = reservationDAO.findAllByRoom(roomService.getByNumber(roomNumber));
        Timeslot timeslot = timeslotService.getByTimeslotID(timeslotID);
        if (userID != 0 && !Objects.equals(timeslot.getUser().getId(), userID)) {
            throw new NoPermissionException("you have no permission");
        }
        for (Reservation reservation : reservations) {
            reservation.removeTimeslot(timeslot);
            reservationDAO.save(reservation);
        }
    }
    private boolean isConflict(ReservationDTO reservationDTO, List<Timeslot> timeslots) {
        for (Timeslot reservationTimeslot : reservationDTO.getTimeslots()) {
            Date stReservation = new Date(reservationTimeslot.getStart()),
                    endReservation = new Date(reservationTimeslot.getEnd());
            for (Timeslot timeslot : timeslots) {
                Date stCur = new Date(timeslot.getStart()),
                        stEnd = new Date(timeslot.getEnd());
                if (stReservation.equals(stCur) || stReservation.equals(stEnd) ||
                    endReservation.equals(stCur) || endReservation.equals(stEnd)) {
                    return true;
                }
                if (((stReservation.after(stCur)) && stReservation.before(stEnd)) ||
                        (endReservation.after(stCur) && endReservation.before(stEnd))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isInside(Long l1, Long r1, Long l2, Long r2) {
        Date st1 = new Date(l1), end1 = new Date(r1);
        Date st2 = new Date(l2), end2 = new Date(r2);
        System.out.println(st1);
        System.out.println(end1);
        System.out.println(st2);
        System.out.println(end2);
        return ((st2.before(st1) || st1.equals(st2)) && (end2.after(end1) || end2.equals(end1)));
    }

    private void addTimeSlots(Reservation reservation, List<Timeslot> timeslots, User user) {
        for (Timeslot timeslot : timeslots) {
            timeslot.setUser(user);
            reservation.addTimeslot(timeslot);
        }
    }
}
