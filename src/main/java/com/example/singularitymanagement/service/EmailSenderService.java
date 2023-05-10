package com.example.singularitymanagement.service;

import com.example.singularitymanagement.DTO.ReservationDTO;
import com.example.singularitymanagement.model.Timeslot;
import com.example.singularitymanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailSenderService {
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(User user, ReservationDTO reservationDTO, String roomNumber) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kazakuper@gmail.com");
        message.setTo(user.getEmail());
        StringBuilder msg = new StringBuilder("You have successfully made reservation for room " + roomNumber + "\nFor time slots: \n");
        for (Timeslot timeslot : reservationDTO.getTimeslots()) {
            Date start = new Date(timeslot.getStart()), end = new Date(timeslot.getEnd());
            msg.append(start).append("-").append(end);
        }
        message.setText(msg.toString());
        message.setSubject("Successful reservation!");
        javaMailSender.send(message);
    }
}
