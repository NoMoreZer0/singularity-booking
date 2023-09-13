package com.example.singularitymanagement.controller;

import com.example.singularitymanagement.model.User;
import com.example.singularitymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userID}")
    public <T> ResponseEntity<T> getUserProfile(@PathVariable Long userID) {
        if (!getUserDetails().getUsername().equals("admin") && !userHaveAccessToId(getUserDetails().getUsername(), userID)) {
            return (ResponseEntity<T>) ResponseEntity.badRequest().body("you have no permission");
        }
        try {
            return (ResponseEntity<T>) ResponseEntity.ok().body(userService.getUserById(userID));
        } catch (Exception e) {
            return (ResponseEntity<T>) ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private boolean userHaveAccessToId(String username, Long targetId) {
        try {
            User user = userService.getUserByUsername(username);
            if (!Objects.equals(user.getId(), targetId)) {
                return Boolean.FALSE;
            }
        } catch (Exception e) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
