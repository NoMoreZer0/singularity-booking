package com.example.singularitymanagement.controller;

import com.example.singularitymanagement.DTO.LoginDTO;
import com.example.singularitymanagement.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public <T> ResponseEntity<T> loginUser(@RequestBody LoginDTO loginDTO) {
        try {
            return (ResponseEntity<T>) ResponseEntity.ok().body(loginService.authenticate(loginDTO));
        } catch (Exception e) {
            return (ResponseEntity<T>) ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
