package com.example.singularitymanagement.service;

import com.example.singularitymanagement.DAO.UserDAO;
import com.example.singularitymanagement.DTO.LoginDTO;
import com.example.singularitymanagement.DTO.TokenDTO;
import com.example.singularitymanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    private AuthenticationManager authenticationManager;;
    private JwtService jwtService;
    private UserDAO userDAO;

    @Autowired
    public LoginService(AuthenticationManager authenticationManager, JwtService jwtService, UserDAO userDAO) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDAO = userDAO;
    }

    public TokenDTO authenticate(LoginDTO loginDTO) throws UsernameNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );
        Optional<User> user = userDAO.findByUsername(loginDTO.getUsername());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("username or password incorrect!");
        }
        String token = jwtService.generateToken(user.get());
        return new TokenDTO(token);
    }
}
