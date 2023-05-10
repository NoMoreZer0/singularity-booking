package com.example.singularitymanagement.model;

import com.example.singularitymanagement.DTO.ReservationDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue
    private Long reservationID;

    @OneToOne
    @JoinColumn(name = "room_number")
    private Room room;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Timeslot> timeslots = new ArrayList<>();

    private void setTimeslots(List<Timeslot> timeslots) { this.timeslots = timeslots; }

    public void addTimeslot(Timeslot timeslot) {
        this.timeslots.add(timeslot);
        timeslot.setReservation(this);
    }

    public void removeTimeslot(Timeslot timeslot) {
        this.timeslots.remove(timeslot);
        timeslot.setReservation(null);
    }

    public Reservation(ReservationDTO reservationDTO, User user) {
        this.timeslots = reservationDTO.getTimeslots();
        for (Timeslot timeslot : timeslots) {
            timeslot.setUser(user);
            timeslot.setReservation(this);
        }
    }
}
