package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.entity.Feature;
import com.anastasiia.entity.Room;
import com.anastasiia.utils.ClassOfRoom;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeatureServiceTest {
    FeatureService featureService;

    @Test
    void getListOfFeatures() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            featureService = new FeatureService();
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                assertEquals(1, featureService.getListOfFeatures().size());
            }
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                doThrow(SQLException.class).when(preparedStatement).executeQuery();
                assertEquals(0, featureService.getListOfFeatures().size());
            }
        }
    }

    private void resultSetMock(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("wifi");
    }

    private PreparedStatement prepareMock(DataSource dataSource) throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(isA(int.class), isA(int.class));
        doNothing().when(preparedStatement).setString(isA(int.class), isA(String.class));
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(connection).commit();
        return preparedStatement;
    }

    @Test
    void testGetListOfFeatures() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            featureService = new FeatureService();
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                assertEquals(1, featureService.getListOfFeatures(1).size());
            }
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                doThrow(SQLException.class).when(preparedStatement).executeQuery();
                assertEquals(0, featureService.getListOfFeatures(1).size());
            }
        }
    }
    @Test
    void getFeaturesForRoom() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                featureService = new FeatureService();
                assertEquals("[Feature{id=1, name='wifi', isChecked=false}]", featureService.getFeaturesForRoom(new String[]{"1"}).toString());
            }
        }
    }

    @Test
    void getFeaturesForRoomEdit() throws SQLException {
        Room room = new Room();
        room.setId(1);
        room.setPrice(1000);
        room.setClassOfRoom(ClassOfRoom.LUX);
        room.setImage("lux.jpg");
        room.setNumberOfPerson(2);
        ArrayList<Feature> features = new ArrayList<>();
        features.add(new Feature(1, "wifi"));
        room.setFeatures(features);
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                resultSetMock(resultSet);
                featureService = new FeatureService();
                assertEquals(1, featureService.getFeaturesForRoomEdit(room).size());
            }
        }
    }

    @Test
    void insertRoomFeatures() {
    }

    @Test
    void updateFeatures() {
    }
}