package com.anastasiia.dao;

import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.Application;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class ApplicationDAOTest {

    @Test
    void insertApplication() throws SQLException{
        Application application = new Application();
        application.setId(6);
        application.setNumberOfGuests(2);
        application.setClassOfRoom(ClassOfRoom.LUX);
        application.setLengthOfStay(2);
        application.setStatus(Status.NEW);
        application.setClientId(1);
        DataSource dataSource = mock(DataSource.class);
        ApplicationDAO applicationDAO = new ApplicationDAO(dataSource);
        try(PreparedStatement ignored = prepareMock(dataSource)){
            assertDoesNotThrow(()->applicationDAO.insertApplication(application));
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
        when(resultSet.getInt("client_id")).thenReturn(1);
        when(resultSet.getInt("number_of_guests")).thenReturn(2);
        when(resultSet.getString("apartment_class")).thenReturn(ClassOfRoom.BUSINESS.name());
        when(resultSet.getString("length_of_stay")).thenReturn("2");
        when(resultSet.getString("status")).thenReturn(Status.NEW.name());
    }

    @Test
    void selectAllApplications() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        ApplicationDAO applicationDAO = new ApplicationDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            List<Application> applicationList = applicationDAO.selectAllApplications();
            assertEquals(1, applicationList.size());
        }
    }

    @Test
    void testSelectAllApplications() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        ApplicationDAO applicationDAO = new ApplicationDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            List<Application> applicationList = applicationDAO.selectAllApplications(1);
            assertEquals(1, applicationList.size());
        }
    }

    @Test
    void testSelectAllApplications1() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        ApplicationDAO applicationDAO = new ApplicationDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            List<Application> applicationList = applicationDAO.selectAllApplications(1,1,"id");
            assertEquals(1, applicationList.size());
        }
    }

    @Test
    void testSelectAllApplications2() throws SQLException, DAOException {
        DataSource dataSource = mock(DataSource.class);
        ApplicationDAO applicationDAO = new ApplicationDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            List<Application> applicationList = applicationDAO.selectAllApplications(1,1,"id",1);
            assertEquals(1, applicationList.size());
        }
    }

    @Test
    void updateStatus() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        ApplicationDAO applicationDAO = new ApplicationDAO(dataSource);
        try(PreparedStatement ignored = prepareMock(dataSource)){
            assertDoesNotThrow(()->applicationDAO.updateStatus(1, Status.CONFIRMED));
        }
    }
}