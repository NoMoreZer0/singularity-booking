package com.example.singularitymanagement.DTO;

import com.example.singularitymanagement.model.Timeslot;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReservationDTO {
    private List<Timeslot> timeslots;
    private String purpose;

    @JsonCreator
    public ReservationDTO(@JsonProperty("timeslots") List<Timeslot> timeslots,
                          @JsonProperty("purpose") String purpose) {
        this.timeslots = timeslots;
        this.purpose = purpose;
    }
}
