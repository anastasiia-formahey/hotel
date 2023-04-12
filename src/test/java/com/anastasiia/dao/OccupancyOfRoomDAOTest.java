package com.anastasiia.dao;

import com.anastasiia.dao.impl.OccupancyOfRoomDAO;
import com.anastasiia.entity.OccupancyOfRoom;
import com.anastasiia.entity.User;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.utils.Role;
import com.anastasiia.utils.Status;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class OccupancyOfRoomDAOTest {

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
        when(resultSet.getInt("room_id")).thenReturn(1);
        when(resultSet.getInt("client_id")).thenReturn(1);
        when(resultSet.getDate("check_in_date")).thenReturn(Date.valueOf("2023-02-25"));
        when(resultSet.getDate("check_out_date")).thenReturn(Date.valueOf("2023-02-27"));
        when(resultSet.getString("status")).thenReturn(Status.FREE.name());
    }

    @Test
    void insertOccupancyOfRoom() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OccupancyOfRoomDAO occupancyOfRoomDAO = new OccupancyOfRoomDAO(dataSource);
        try(PreparedStatement ignored = prepareMock(dataSource)){
            assertDoesNotThrow(()->occupancyOfRoomDAO.insertOccupancyOfRoom(1,1,
                    Date.valueOf("2023-02-25"), Date.valueOf("2023-02-27"), Status.BOOKED));
        }
    }

    @Test
    void updateStatus() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        OccupancyOfRoomDAO occupancyOfRoomDAO = new OccupancyOfRoomDAO(dataSource);
        try(PreparedStatement ignored = prepareMock(dataSource)){
            assertDoesNotThrow(()->occupancyOfRoomDAO.updateStatus(1, Status.BOOKED,Date.valueOf("2023-02-25"), Date.valueOf("2023-02-27")));
        }
    }

    @Test
    void isExist() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        OccupancyOfRoomDAO occupancyOfRoomDAO = new OccupancyOfRoomDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            assertTrue(occupancyOfRoomDAO.isExist(1,1,
                    Date.valueOf("2023-02-25"), Date.valueOf("2023-02-27")));
        }
    }

    @Test
    void getStatus() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        OccupancyOfRoomDAO occupancyOfRoomDAO = new OccupancyOfRoomDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            assertEquals(Status.FREE,
                    occupancyOfRoomDAO.getStatus(1,Date.valueOf("2023-02-25")));
        }
    }

    @Test
    void selectByRoomId() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        OccupancyOfRoomDAO occupancyOfRoomDAO = new OccupancyOfRoomDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            when(resultSet.getInt(Fields.OCCUPANCY_OF_ROOM_CLIENT_ID)).thenReturn(1);
            when(resultSet.getString(Fields.USER_FIRST_NAME)).thenReturn("Test");
            when(resultSet.getString(Fields.USER_LAST_NAME)).thenReturn("Test");
            when(resultSet.getString(Fields.USER_EMAIL)).thenReturn("test@gmail.com");
            when(resultSet.getString(Fields.USER_ROLE)).thenReturn(String.valueOf(Role.CLIENT));
            Map<OccupancyOfRoom, User> occupancyOfRoomUserMap = occupancyOfRoomDAO.selectByRoomId(1,Date.valueOf("2023-02-25"));
            assertEquals(1,
                    occupancyOfRoomUserMap.size());
        }
    }
}