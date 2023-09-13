package com.example.singularitymanagement.controller;

import com.example.singularitymanagement.DTO.UserDTO;
import com.example.singularitymanagement.exception.UserNameOrEmailExistsException;
import com.example.singularitymanagement.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private RegistrationService registrationService;

    @Autowired
    public RegisterController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) throws UserNameOrEmailExistsException {
        registrationService.saveUser(userDTO);
        return ResponseEntity.ok().body("user registered successfully!");
    }
}
