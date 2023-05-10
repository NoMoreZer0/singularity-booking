package com.example.singularitymanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.*;

@Entity
@Data
@NoRepositoryBean
@Table(name = "timeslot")
public class Timeslot implements Comparable<Timeslot>{
    @Id
    @GeneratedValue
    private Long timeslotID;

    private Long start;

    private Long end;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    @JsonBackReference
    private Reservation reservation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public int compareTo(Timeslot o) {
        return start.compareTo(o.getStart());
    }
}
