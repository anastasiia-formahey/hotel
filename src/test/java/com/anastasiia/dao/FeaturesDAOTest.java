package com.anastasiia.dao;

import com.anastasiia.dao.impl.FeaturesDAO;
import com.anastasiia.entity.Feature;
import com.anastasiia.entity.Room;
import com.anastasiia.exceptions.DAOException;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class FeaturesDAOTest {

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
    private void resultSetMock(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("wifi");
    }
    @Test
    void selectAll() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        FeaturesDAO featuresDAO = new FeaturesDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            List<Feature> features = featuresDAO.selectAll();
            assertEquals(1, features.size());
        }
    }
    @Test
    void selectAllById() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        FeaturesDAO featuresDAO = new FeaturesDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            List<Feature> features = featuresDAO.selectAll(1);
            assertEquals(1, features.size());
        }
    }

    @Test
    void insertRoomFeatures() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        FeaturesDAO featuresDAO = new FeaturesDAO(dataSource);
        try(PreparedStatement ignored = prepareMock(dataSource)){
            assertDoesNotThrow(()->featuresDAO.insertRoomFeatures(new Room(), new Feature(1, "wifi")));
        }
    }
    @Test
    void updateRoomFeatures() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        FeaturesDAO featuresDAO = new FeaturesDAO(dataSource);
        try(PreparedStatement ignored = prepareMock(dataSource)){
            assertDoesNotThrow(()->featuresDAO.updateRoomFeatures(new Room(), new ArrayList<>()));
        }
    }
}