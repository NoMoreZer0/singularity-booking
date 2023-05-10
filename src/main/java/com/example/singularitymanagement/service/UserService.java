package com.example.singularitymanagement.service;

import com.example.singularitymanagement.DAO.UserDAO;
import com.example.singularitymanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUserById(Long id) throws UsernameNotFoundException {
        Optional<User> user = userDAO.findById(id);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Id not found!");
        }
        return user.get();
    }
}
