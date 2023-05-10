package com.example.singularitymanagement.DTO;

import com.example.singularitymanagement.model.Facility;
import com.example.singularitymanagement.model.Image;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RoomDTO {
    private String roomNumber;
    private String floor;
    private String area;
    private String capacity;
    private String description;
    private List<Facility> facilities;
    private List<Image> images;

    @JsonCreator
    public RoomDTO(@JsonProperty("roomNumber") String roomNumber, @JsonProperty("floor") String floor,
                   @JsonProperty("area") String area, @JsonProperty("capacity") String capacity,
                   @JsonProperty("description") String description, @JsonProperty("facilities") List<Facility> facilities,
                   @JsonProperty("images") List<Image> images) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.area = area;
        this.capacity = capacity;
        this.description = description;
        this.facilities = facilities;
        this.images = images;
    }
}
