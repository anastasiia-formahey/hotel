package com.anastasiia.dao;

import com.anastasiia.entity.Booking;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class BookingDAOTest {

    @Test
    void insertBooking() throws SQLException {
        Booking booking = new Booking();
        booking.setRoomId(200);
        booking.setClientId(2);
        booking.setCheckInDate(Date.valueOf("2023-02-25"));
        booking.setCheckOutDate(Date.valueOf("2023-02-27"));
        booking.setPrice(200.0);
        booking.setDateOfBooking(Date.valueOf("2023-02-20"));
        booking.setStatusOfBooking(Status.BOOKED);
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        DataSource dataSource = mock(DataSource.class);
        BookingDAO bookingDAO = new BookingDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getInt("number_of_person")).thenReturn(1);
            when(resultSet.getDouble("price")).thenReturn(200.0);
            when(resultSet.getString("class_of_room")).thenReturn(ClassOfRoom.BUSINESS.name());
            when(resultSet.getString("image")).thenReturn("image.jpg");

            assertDoesNotThrow(()-> bookingDAO.insertBooking(bookingList));
        }


    }

    @Test
    void insertBooking2() throws SQLException {
        Booking booking = new Booking();
        booking.setRoomId(200);
        booking.setClientId(2);
        booking.setCheckInDate(Date.valueOf("2023-02-25"));
        booking.setCheckOutDate(Date.valueOf("2023-02-27"));
        booking.setPrice(200.0);
        booking.setDateOfBooking(Date.valueOf("2023-02-20"));
        booking.setStatusOfBooking(Status.BOOKED);
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        DataSource dataSource = mock(DataSource.class);
        BookingDAO bookingDAO = new BookingDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getInt("number_of_person")).thenReturn(1);
            when(resultSet.getDouble("price")).thenReturn(200.0);
            when(resultSet.getString("class_of_room")).thenReturn(ClassOfRoom.BUSINESS.name());
            when(resultSet.getString("image")).thenReturn("image.jpg");

            assertDoesNotThrow(()-> bookingDAO.insertBooking(bookingList, true));
        }


    }
    private PreparedStatement prepareMock(DataSource dataSource) throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
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
        when(resultSet.getInt(Fields.BOOKING_ID)).thenReturn(1);
        when(resultSet.getInt(Fields.BOOKING_ROOM_ID)).thenReturn(1);
        when(resultSet.getInt(Fields.BOOKING_CLIENT_ID)).thenReturn(1);
        when(resultSet.getDate(Fields.BOOKING_CHECK_IN)).thenReturn(Date.valueOf("2023-02-25"));
        when(resultSet.getDate(Fields.BOOKING_CHECK_OUT)).thenReturn(Date.valueOf("2023-02-27"));
        when(resultSet.getDouble(Fields.BOOKING_PRICE)).thenReturn(200.0);
        when(resultSet.getDate(Fields.BOOKING_DATE_OF_BOOKING)).thenReturn(Date.valueOf("2023-02-20"));
        when(resultSet.getString(Fields.BOOKING_STATUS)).thenReturn(Status.BOOKED.name());
    }

    @Test
    void updateStatus() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        BookingDAO bookingDAO = new BookingDAO(dataSource);
        try(PreparedStatement ignored = prepareMock(dataSource)){
            assertDoesNotThrow(()-> bookingDAO.updateStatus(1, Status.BOOKED));
        }
    }

    @Test
    void selectAll() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        BookingDAO bookingDAO = new BookingDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            List<Booking> bookingList = bookingDAO.selectAll();
            assertEquals(1, bookingList.size());

        }
    }
    @Test
    void selectAll2() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        BookingDAO bookingDAO = new BookingDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            List<Booking> bookingList = bookingDAO.selectAll(1,1,"id");
            assertEquals(1, bookingList.size());
        }
    }
    @Test
    void selectAllByUserId() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        BookingDAO bookingDAO = new BookingDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            List<Booking> bookingList = bookingDAO.selectAllByUserId(1);
            assertEquals(1, bookingList.size());
        }
    }

    @Test
    void selectAllByUserId2() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        BookingDAO bookingDAO = new BookingDAO(dataSource);
        try(PreparedStatement preparedStatement = prepareMock(dataSource)){
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            resultSetMock(resultSet);
            List<Booking> bookingList = bookingDAO.selectAllByUserId(1,1,"id",1);
            assertEquals(1, bookingList.size());
        }
    }
}