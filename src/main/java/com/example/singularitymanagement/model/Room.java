package com.example.singularitymanagement.model;

import com.example.singularitymanagement.DTO.RoomDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "room")
public class Room {
    @Id
    private String roomNumber;
    private String floor;
    private String area;
    private String capacity;
    @Lob
    private String description;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Facility> facilities = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Image> images = new ArrayList<>();
    public Room(RoomDTO roomDTO) {
        this.roomNumber = roomDTO.getRoomNumber();
        this.floor = roomDTO.getFloor();
        this.area = roomDTO.getArea();
        this.capacity = roomDTO.getCapacity();
        this.description = roomDTO.getDescription();
        this.facilities = roomDTO.getFacilities();
        this.images = roomDTO.getImages();
        for (Facility facility : facilities) {
            facility.setRoom(this);
        }
        for (Image image : images) {
            image.setRoom(this);
        }
    }
    private void setFacilities(List<Facility> facilities) { this.facilities = facilities; }

    private void setImages(List<Image> images) { this.images = images; }
    public void addFacility(Facility facility) {
        this.facilities.add(facility);
        facility.setRoom(this);
    }

    public void removeFacility(Facility facility) {
        this.facilities.remove(facility);
        facility.setRoom(null);
    }

    public void addImage(Image image) {
        this.images.add(image);
        image.setRoom(this);
    }

    public void removeImage(Image image) {
        this.images.remove(image);
        image.setRoom(null);
    }
}
