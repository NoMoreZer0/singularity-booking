package com.example.singularitymanagement.DTO;

import com.example.singularitymanagement.model.Timeslot;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeslotDTO implements Comparable<TimeslotDTO> {
    private Long start;
    private Long end;
    private Long userID;
    private Long timeslotID;
    private String purpose;

    @JsonCreator
    public TimeslotDTO(@JsonProperty("start") Long start, @JsonProperty("end") Long end, @JsonProperty("userID") Long userID, @JsonProperty("timeslotID") Long timeslotID, @JsonProperty("purpose") String purpose) {
        this.start = start;
        this.end = end;
        this.userID = userID;
        this.timeslotID = timeslotID;
        this.purpose = purpose;
    }

    public TimeslotDTO(Timeslot timeslot) {
        this.start = timeslot.getStart();
        this.end = timeslot.getEnd();
        this.userID = timeslot.getUser().getId();
        this.timeslotID = timeslot.getTimeslotID();
        this.purpose = timeslot.getPurpose();
    }

    @Override
    public int compareTo(TimeslotDTO o) {
        return start.compareTo(o.getStart());
    }
}
