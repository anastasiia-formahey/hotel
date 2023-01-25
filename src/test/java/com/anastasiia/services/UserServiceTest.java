package com.anastasiia.services;

import com.anastasiia.dao.UserDAO;
import com.anastasiia.entity.User;
import com.anastasiia.utils.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {
    UserService userService;
    UserDAO userDAO;
    User user;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userDAO = mock(UserDAO.getInstance().getClass());
    }

    @AfterEach
    void tearDown() {
        userService = null;
    }

    @Test
    void validateUserByEmail() {
        assertEquals(false,userService.validateUserByEmail("test@gmail.com"));
    }

    @Test
    void validateUserByEmailAndPassword() {
        assertEquals(false,
                userService.validateUserByEmailAndPassword("test@gmail.com", "MTExMQ=="));
    }

    @Test
    void getRole() {
        assertEquals(null,
                userService.getRole("test@gmail.com"));

    }

    @ParameterizedTest
    @CsvSource({"test@gmail.com"})
    void getId(String email) throws SQLException {
        when(UserDAO.findUserByEmail(email)).thenReturn(user);
        user = UserDAO.findUserByEmail(email);
        assertEquals(6, user.getId());
    }

    @Test
    void getUser() {
    }

    @Test
    void testGetUser() {
    }

    @Test
    void insertUser() {
    }
}