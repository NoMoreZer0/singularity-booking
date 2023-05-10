package com.example.singularitymanagement.service;

import com.example.singularitymanagement.DAO.UserDAO;
import com.example.singularitymanagement.DTO.UserDTO;
import com.example.singularitymanagement.exception.UserNameOrEmailExistsException;
import com.example.singularitymanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(UserDTO userDTO) throws Exception {
        User user = new User(userDTO);
        if (userDAO.existsByEmail(userDTO.getEmail()) || userDAO.existsByUsername(userDTO.getUsername())) {
            throw new UserNameOrEmailExistsException("username or email already exists!");
        }
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);
        userDAO.save(user);
    }
}
