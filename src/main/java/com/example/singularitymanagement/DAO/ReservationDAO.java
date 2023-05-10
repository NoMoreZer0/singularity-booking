package com.example.singularitymanagement.DAO;

import com.example.singularitymanagement.model.Reservation;
import com.example.singularitymanagement.model.Room;
import com.example.singularitymanagement.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationDAO extends CrudRepository<Reservation, Long> {
    List<Reservation> findAllByRoom(Room room);

}
