package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.Fields;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.User;
import com.anastasiia.exceptions.InvalidUserException;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.services.impl.UserService;
import com.anastasiia.utils.Role;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;


import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    UserService userService;

    @Test
    void validateUserByEmail() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
           userService = new UserService();
            try(PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                when(resultSet.next()).thenReturn(false);
                assertDoesNotThrow(() ->
                        userService.validateUserByEmail("test10@gmail.com"));
            }
            try(PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                assertThrows(InvalidUserException.class, () ->
                        userService.validateUserByEmail("test@gmail.com"));
            }
            try(PreparedStatement ignored = prepareMockThrowException(dataSource)) {
                assertThrows(InvalidUserException.class, () ->
                        userService.validateUserByEmail("test@gmail.com"));
            }
        }

    }

    @Test
    void validateUserByEmailAndPassword() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            userService = new UserService();
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                assertDoesNotThrow(() ->
                        userService.validateUserByEmailAndPassword("test@gmail.com", "1111"));
            }
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                assertThrows(InvalidUserException.class, () ->
                        userService.validateUserByEmailAndPassword("test@gmail.com", "0000"));
            }
            try (PreparedStatement ignored = prepareMockThrowException(dataSource)) {
                assertThrows(InvalidUserException.class, () ->
                        userService.validateUserByEmailAndPassword("test@gmail.com", "0000"));
            }
        }
    }


    @Test
    void getRole() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            userService = new UserService();
            try(PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                assertDoesNotThrow(() ->
                        userService.getRole("test@gmail.com"));
            }
            try(PreparedStatement preparedStatement = prepareMock(dataSource)) {
                doThrow(SQLException.class).when(preparedStatement).executeQuery();
                assertThrows(ServiceException.class, () ->
                        userService.getRole("test@gmail.com"));
            }
        }
    }

    @Test
    void insertUser() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            userService = new UserService();
            User user = new User("Test", "Test", "testEmail@gmail.com", "MTExMQ==", Role.CLIENT);
            try(PreparedStatement ignored = prepareMock(dataSource)) {
                assertDoesNotThrow(() ->
                        userService.insertUser(user));
            }
            try(PreparedStatement ignored = prepareMockThrowException(dataSource)) {
                assertThrows(ServiceException.class, () ->
                        userService.insertUser(user));
            }
        }
    }

    @Test
    void entityToDTO() throws SQLException {
        User user = new User("Test", "Test", "test@gmail.com", "MTExMQ==", Role.CLIENT);
        user.setId(1);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setFirstName("Test");
        userDTO.setLastName("Test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setRole(Role.CLIENT);
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            userService = new UserService();
            try(PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                assertEquals(userDTO.toString(), userService.entityToDTO(user).toString());
            }
        }
    }

    @Test
    void dtoToEntity() throws SQLException {
        User user = new User("Test", "Test", "test@gmail.com", "MTExMQ==", Role.CLIENT);
        user.setId(1);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setFirstName("Test");
        userDTO.setLastName("Test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setRole(Role.CLIENT);

        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            userService = new UserService();
            try(PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                assertDoesNotThrow(() ->
                        userService.dtoToEntity(userDTO));
            }
            try(PreparedStatement ignored = prepareMockThrowException(dataSource)) {
                assertThrows(ServiceException.class, () ->
                        userService.dtoToEntity(userDTO));
            }
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
    private PreparedStatement prepareMockThrowException(DataSource dataSource) throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
        doThrow(SQLException.class).when(preparedStatement).setInt(isA(int.class), isA(int.class));
        doThrow(SQLException.class).when(preparedStatement).setLong(isA(int.class), isA(long.class));
        doThrow(SQLException.class).when(preparedStatement).setString(isA(int.class), isA(String.class));
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
        when(resultSet.getString(Fields.USER_PASSWORD)).thenReturn("MTExMQ==");
        when(resultSet.getString(Fields.USER_ROLE)).thenReturn(Role.CLIENT.name());
    }
}