package com.example.singularitymanagement.DTO;

import com.example.singularitymanagement.model.Reservation;
import com.example.singularitymanagement.model.Timeslot;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class ReservationResponseDTO {
    private Long reservationID;
    private List<TimeslotDTO> timeslots = new ArrayList<>();
    private String roomNumber;

    public ReservationResponseDTO(Reservation reservation) {
        this.reservationID = reservation.getReservationID();
        for (Timeslot timeslot : reservation.getTimeslots()) {
            timeslots.add(new TimeslotDTO(timeslot));
        }
        Collections.sort(timeslots);
        this.roomNumber = reservation.getRoom().getRoomNumber();
    }
}
