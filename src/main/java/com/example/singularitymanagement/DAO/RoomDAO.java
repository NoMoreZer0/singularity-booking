package com.example.singularitymanagement.DAO;

import com.example.singularitymanagement.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomDAO extends CrudRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);
}
