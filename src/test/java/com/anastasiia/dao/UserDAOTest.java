package com.anastasiia.dao;

import com.anastasiia.entity.User;
import com.anastasiia.utils.Role;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOTest {
    DataSource dataSource;
    User user;
    UserDAO userDAO;

    @Test
    void insertUser() throws SQLException {
        user = new User("Test", "Test", "testEmail.gmail.com", "MTExMQ==", Role.CLIENT);
        dataSource = mock(DataSource.class);
        userDAO = new UserDAO(dataSource);
        try(PreparedStatement ignored = prepareMock(dataSource)){
            assertDoesNotThrow(()->userDAO.insertUser(user));
        }
    }
    private PreparedStatement prepareMock(DataSource dataSource) throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(isA(int.class), isA(int.class));
        doNothing().when(preparedStatement).setLong(isA(int.class), isA(long.class));
        doNothing().when(preparedStatement).setString(isA(int.class), isA(String.class));
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(connection).commit();
        return preparedStatement;
    }
    private void resultSetMock(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(Fields.USER_ID)).thenReturn(1);
        when(resultSet.getString(Fields.USER_FIRST_NAME)).thenReturn("Test");
        when(resultSet.getString(Fields.USER_LAST_NAME)).thenReturn("Test");
        when(resultSet.getString(Fields.USER_EMAIL)).thenReturn("test@gmail.com");
        when(resultSet.getString(Fields.USER_PASSWORD)).thenReturn("0000");
        when(resultSet.getString(Fields.USER_ROLE)).thenReturn(Role.CLIENT.name());
    }

    @Test
    void findUserById() throws SQLException {
        dataSource = mock(DataSource.class);
        userDAO = new UserDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            assertDoesNotThrow(()->userDAO.findUserById(1));
        }

    }
    @Test
    void findUserByEmail() throws SQLException {
        dataSource = mock(DataSource.class);
        userDAO = new UserDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            assertDoesNotThrow(()->userDAO.findUserByEmail("email@email.com"));
        }
    }
}