package com.example.singularitymanagement.DAO;

import com.example.singularitymanagement.model.Timeslot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeslotDAO extends CrudRepository<Timeslot, Long> {

}
