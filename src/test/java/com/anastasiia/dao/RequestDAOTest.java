package com.anastasiia.dao;

import com.anastasiia.entity.Request;
import com.anastasiia.utils.Status;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class RequestDAOTest {
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
        when(resultSet.getInt("application_id")).thenReturn(1);
        when(resultSet.getDate("check_in_date")).thenReturn(Date.valueOf("2023-02-25"));
        when(resultSet.getDate("check_out_date")).thenReturn(Date.valueOf("2023-02-27"));
        when(resultSet.getInt("room_id")).thenReturn(1);
        when(resultSet.getString("status")).thenReturn(Status.NEW.name());
    }
    @Test
    void insertRequest() throws SQLException {
        Request request = new Request();
        request.setRoomId(1);
        request.setStatus(Status.NOT_CONFIRMED);
        request.setCheckInDate(Date.valueOf("2023-02-25"));
        request.setCheckOutDate(Date.valueOf("2023-02-28"));
        request.setApplicationId(2);
        List<Request> requestList = new ArrayList<>();
        requestList.add(request);
        DataSource dataSource = mock(DataSource.class);
        RequestDAO requestDAO = new RequestDAO(dataSource);
        try(PreparedStatement ignored = prepareMock(dataSource)){
            assertDoesNotThrow(()->requestDAO.insertRequest(requestList));
        }
    }

    @Test
    void updateStatus() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        RequestDAO requestDAO = new RequestDAO(dataSource);
        try(PreparedStatement ignored = prepareMock(dataSource)){
            assertDoesNotThrow(()->requestDAO.updateStatus(1, Status.CONFIRMED));
        }
    }

    @Test
    void selectByApplicationId() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        RequestDAO requestDAO = new RequestDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            List<Request> requestList = requestDAO.selectByApplicationId(1);
            assertEquals(1, requestList.size());
        }
    }
}