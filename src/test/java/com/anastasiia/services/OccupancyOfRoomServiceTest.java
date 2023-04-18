package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.impl.OccupancyOfRoomDAO;
import com.anastasiia.dto.OccupancyOfRoomDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.services.impl.OccupancyOfRoomService;
import com.anastasiia.utils.Status;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

class OccupancyOfRoomServiceTest {

    OccupancyOfRoomDAO occupancyOfRoomDAO;
    OccupancyOfRoomService occupancyOfRoomService;
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
        when(resultSet.getInt("room_id")).thenReturn(1);
        when(resultSet.getInt("client_id")).thenReturn(1);
        when(resultSet.getDate("check_in_date")).thenReturn(Date.valueOf("2023-02-25"));
        when(resultSet.getDate("check_out_date")).thenReturn(Date.valueOf("2023-02-27"));
        when(resultSet.getString("status")).thenReturn(Status.FREE.name());
    }
    @Test
    void insertOccupancyOfRoom() throws SQLException {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setFirstName("Test");
        userDTO.setLastName("Test");
        userDTO.setEmail("test@gmail.com");
        OccupancyOfRoomDTO occupancyOfRoomDTO = new OccupancyOfRoomDTO(1, userDTO, Date.valueOf("2023-03-24"), Date.valueOf("2023-03-24"), Status.BOOKED);
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)){
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            occupancyOfRoomService = new OccupancyOfRoomService();
            try(PreparedStatement preparedStatement = prepareMock(dataSource)){
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                assertDoesNotThrow(()->
                        occupancyOfRoomService.insertOccupancyOfRoom(
                                occupancyOfRoomDTO.getRoomId(),
                                userDTO.getId(),
                                occupancyOfRoomDTO.getCheckInDate(),
                                occupancyOfRoomDTO.getCheckOutDate(),
                                occupancyOfRoomDTO.getStatus()));
            }
            try(PreparedStatement preparedStatement = prepareMockThrowException(dataSource)){
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                assertDoesNotThrow(()->
                        occupancyOfRoomService.insertOccupancyOfRoom(
                                occupancyOfRoomDTO.getRoomId(),
                                userDTO.getId(),
                                occupancyOfRoomDTO.getCheckInDate(),
                                occupancyOfRoomDTO.getCheckOutDate(),
                                occupancyOfRoomDTO.getStatus()));
            }
        }
    }

    @Test
    void getOccupancyOfRoomByRoomId() {
    }

    @Test
    void updateStatus() throws SQLException {
        int roomId = 1;
        int clientId = 1;
        Date checkIn = Date.valueOf("2023-03-24");
        Date checkOut = Date.valueOf("2023-03-26");
        Status status = Status.BOOKED;
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)){
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            occupancyOfRoomService = new OccupancyOfRoomService();
            try(PreparedStatement preparedStatement = prepareMock(dataSource)){
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                assertDoesNotThrow(()->
                        occupancyOfRoomService.updateStatus(roomId,  status, checkIn, checkOut));
            }
            try(PreparedStatement preparedStatement = prepareMockThrowException(dataSource)){
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                assertThrows(ServiceException.class, ()->
                        occupancyOfRoomService.updateStatus(roomId,  status, checkIn, checkOut));
            }
        }
    }
}