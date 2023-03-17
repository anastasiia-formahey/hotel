package com.anastasiia.dao;

import com.anastasiia.entity.Application;
import com.anastasiia.entity.Room;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomDAOTest {

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
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("number_of_person")).thenReturn(2);
        when(resultSet.getDouble("price")).thenReturn(1000.0);
        when(resultSet.getString("class_of_room")).thenReturn(ClassOfRoom.BUSINESS.name());
        when(resultSet.getString("image")).thenReturn("image.jsp");
    }
    @Test
    void insertRoom() {

    }

    @Test
    void selectAllRooms() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        RoomDAO roomDAO = new RoomDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            List<Room> roomList = roomDAO.selectAllRooms();
            assertEquals(1, roomList.size());
        }
    }

    @Test
    void findRoomById() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        RoomDAO roomDAO = new RoomDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            Room room = roomDAO.findRoomById(1);
            Room room1 = new Room(2,1000.0, ClassOfRoom.BUSINESS, "image.jsp");
            room1.setId(1);
            assertEquals(room1.toString(), room.toString());
        }
    }

    @Test
    void updateRoom() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        RoomDAO roomDAO = new RoomDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            Room room1 = new Room(2,1000.0, ClassOfRoom.BUSINESS, "image.jsp");
            room1.setId(1);
            assertDoesNotThrow(()-> roomDAO.updateRoom(room1));
        }
    }

    @Test
    void testSelectAllRooms() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        RoomDAO roomDAO = new RoomDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            List<Room> roomList = roomDAO.selectAllRooms(1,10, "id");
            assertEquals(1, roomList.size());
        }
    }

    @Test
    void selectRoomsForBooking() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        RoomDAO roomDAO = new RoomDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            List<Room> roomList = roomDAO.selectRoomsForBooking(2, Date.valueOf("2023-02-25"), Date.valueOf("2023-02-27"));
            assertEquals(1, roomList.size());
        }
    }

    @Test
    void countAllRooms() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        RoomDAO roomDAO = new RoomDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getInt("COUNT(id)")).thenReturn(5);
            int result = roomDAO.countAllRooms();
            assertEquals(5, result);
        }
    }
}