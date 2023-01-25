package com.anastasiia.dao;

import com.anastasiia.entity.User;
import com.anastasiia.utils.Fields;
import com.anastasiia.utils.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserDAOTest {
    DBManager dataSource;
    Connection mockConn;
    PreparedStatement mockPreparedStmnt;
    ResultSet mockResultSet;
    User user;
    UserDAO userDAO;

    @BeforeEach
    void setUp() throws SQLException {
        when(dataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(any(String.class))).thenReturn(mockPreparedStmnt);

        user = new User("Test", "Test", "testEmail.gmail.com", "MTExMQ==", Role.CLIENT);
    }

    @AfterEach
    void tearDown() {
        mockConn = null;
        mockPreparedStmnt = null;
        mockResultSet = null;
    }

    @Test
    void insertUser() throws SQLException {
        userDAO.getInstance().insertUser(user);
    }

    @Test
    void findUserById() {
    }

    @Test
    void findUserByEmail() {
    }
}