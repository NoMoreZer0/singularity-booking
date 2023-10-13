package com.example.singularitymanagement.DAO;

import com.example.singularitymanagement.model.User;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUserDAO {
    @Autowired
    private UserDAO userDAO;

    @Test
    @DisplayName("test find by username")
    public void findByUserNameTest() {
        try {
            Optional<User> opt = userDAO.findByUsername("test@test.kz");
            assertTrue(opt.isEmpty());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("test exists by username")
    public void existsByUserNameTest() {
        try {
            assertFalse(userDAO.existsByUsername("testusername"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("test exists by email")
    public void existsByEmailTest() {
        try {
            assertFalse(userDAO.existsByEmail("test@test.kz"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
