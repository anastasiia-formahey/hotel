package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.entity.Feature;
import com.anastasiia.entity.Room;
import com.anastasiia.exceptions.NoResultException;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.sql.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceTest {
    RoomService roomService;
    @Test
    void dtoToEntity() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(1);
        roomDTO.setClassOfRoom(ClassOfRoom.BUSINESS);
        roomDTO.setPrice(1000);
        roomDTO.setNumberOfPerson(2);
        roomDTO.setImage("business.jpg");
        ArrayList<Feature> features = new ArrayList<>();
        features.add(new Feature(1, "Free wi-fi"));
        roomDTO.setFeatures(features);
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            roomService = new RoomService();
            assertEquals("Room{id=1, numberOfPerson=2, price=1000.0, classOfRoom=BUSINESS, image='business.jpg', features=[Feature{id=1, name='Free wi-fi', isChecked=false}]}",
                    roomService.dtoToEntity(roomDTO).toString());
        }
    }
    @Test
    void insertRoom() {
    }

    @Test
    void editRoom() {
    }

    @Test
    void selectAllRooms() throws SQLException, ServiceException {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            roomService = new RoomService();
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                List<Room> roomList = roomService.selectAllRooms();
                assertEquals(1, roomList.size());
            }
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                doThrow(SQLException.class).when(preparedStatement).executeQuery();
                assertThrows(ServiceException.class, ()->
                        roomService.selectAllRooms());
            }
        }

}
    @Test
    void selectAllRoomsDTO() {
    }

    @Test
    void testSelectAllRooms() {
    }

    @Test
    void countAllRooms() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            roomService = new RoomService();
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                when(resultSet.next()).thenReturn(true).thenReturn(false);
                when(resultSet.getInt("COUNT(id)")).thenReturn(5);
                assertEquals(5, roomService.countAllRooms());
            }
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                doThrow(SQLException.class).when(preparedStatement).executeQuery();
                assertEquals(0, roomService.countAllRooms());
            }
        }
    }

    @Test
    void findRoomForBooking() throws SQLException, ServiceException {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            roomService = new RoomService();
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                List<RoomDTO> roomList = roomService.findRoomForBooking(2, Date.valueOf("2023-02-02"), Date.valueOf("2023-02-04"));
                assertEquals(1, roomList.size());
            }
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                when(resultSet.next()).thenReturn(false);
                assertThrows(NoResultException.class, ()->
                        roomService.findRoomForBooking(1, Date.valueOf("2023-02-02"), Date.valueOf("2023-02-04")));
            }
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                doThrow(SQLException.class).when(preparedStatement).executeQuery();
                assertThrows(ServiceException.class, ()->
                        roomService.findRoomForBooking(1, Date.valueOf("2023-02-02"), Date.valueOf("2023-02-04")));
            }
        }
    }

    @Test
    void findRoomById() throws SQLException, ServiceException {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            roomService = new RoomService();
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                assertEquals(1, roomService.findRoomById(1).getId());
            }
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                doThrow(SQLException.class).when(preparedStatement).executeQuery();
                assertThrows(ServiceException.class, ()-> roomService.findRoomById(1));
            }
        }
    }

    @Test
    void entityToDTO() {
        Room room = new Room();
        room.setId(1);
        room.setPrice(1000);
        room.setClassOfRoom(ClassOfRoom.LUX);
        room.setImage("lux.jpg");
        room.setNumberOfPerson(2);
        ArrayList<Feature> features = new ArrayList<>();
        features.add(new Feature(1, "Free wi-fi"));
        room.setFeatures(features);
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            roomService = new RoomService();
            assertEquals(RoomDTO.class, roomService.entityToDTO(room).getClass());
        }
    }

    @Test
    void getRoomMap() throws SQLException, ServiceException {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            roomService = new RoomService();
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                when(resultSet.next()).thenReturn(true).thenReturn(false);
                when(resultSet.getInt("id")).thenReturn(1);
                when(resultSet.getString("status")).thenReturn(Status.FREE.name());
                Map<Integer, Status> integerStatusMap = roomService.getRoomMap(Date.valueOf("2023-02-22"));
                assertEquals("[1=FREE]", integerStatusMap.entrySet().toString());
            }
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                doThrow(SQLException.class).when(preparedStatement).executeQuery();
                assertThrows(ServiceException.class, ()-> roomService.getRoomMap(Date.valueOf("2023-02-22")));
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

    private void resultSetMock(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("number_of_person")).thenReturn(2);
        when(resultSet.getDouble("price")).thenReturn(1000.0);
        when(resultSet.getString("class_of_room")).thenReturn(ClassOfRoom.BUSINESS.name());
        when(resultSet.getString("image")).thenReturn("image.jsp");
    }
}