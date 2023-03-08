package com.anastasiia.services;

import com.anastasiia.dao.UserDAO;
import com.anastasiia.entity.User;
import com.anastasiia.exceptions.InvalidUserException;
import com.anastasiia.exceptions.ServiceException;
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

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        UserDAO userDAO= mock(UserDAO.class);
    }

    @AfterEach
    void tearDown() {
        userService = null;
    }

    @Test
    void validateUserByEmail() throws InvalidUserException {
        assertEquals(false,userService.validateUserByEmail("test@gmail.com"));
    }

    @Test
    void validateUserByEmailAndPassword() throws InvalidUserException {
        assertEquals(false,
                userService.validateUserByEmailAndPassword("test@gmail.com", "MTExMQ=="));
    }

    @Test
    void getRole() throws ServiceException {
        assertEquals(null,
                userService.getRole("test@gmail.com"));

    }

}