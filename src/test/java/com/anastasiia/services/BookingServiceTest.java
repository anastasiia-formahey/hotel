package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.Booking;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    BookingService bookingService;
    Booking booking;
    RequestDTO requestDTO;
    UserDTO userDTO;
    RoomDTO roomDTO;

    @BeforeEach
    void setUp() {
        booking = new Booking();
        booking.setId(1);
        booking.setRoomId(1);
        booking.setClientId(1);
        booking.setCheckInDate(Date.valueOf("2023-02-25"));
        booking.setCheckOutDate(Date.valueOf("2023-02-26"));
        booking.setPrice(100.0);
        booking.setDateOfBooking(Date.valueOf("2023-01-20"));
        booking.setStatusOfBooking(Status.BOOKED);
        roomDTO = new RoomDTO();
        roomDTO.setId(1);
        roomDTO.setClassOfRoom(ClassOfRoom.LUX);
        roomDTO.setNumberOfPerson(2);
        roomDTO.setImage("lux.jpg");
        roomDTO.setPrice(1000);
        requestDTO = new RequestDTO();
        requestDTO.setApplicationId(2);
        requestDTO.setStatusOfRequest(Status.NOT_CONFIRMED);
        requestDTO.setRequestElements(roomDTO, Date.valueOf("2023-02-25"), Date.valueOf("2023-02-27"));
    }

    @Test
    void withDrawnBooking() {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);

            bookingService = new BookingService();
            assertFalse(bookingService.withDrawnBooking(booking));
            booking.setDateOfBooking(Date.valueOf("2023-02-20"));
            booking.setBookingExpirationDate();
        }
    }

    @Test
    void getTotalCost() {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            bookingService = new BookingService();
            assertEquals(2000.0,
                    bookingService.getTotalCost(
                            1000.0,
                            Date.valueOf("2023-02-02"),
                            Date.valueOf("2023-02-04")));
        }
    }

    @Test
    void getBookingFromRequest() {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(1);
        bookingDTO.setRoomId(1);
        bookingDTO.setRoom(roomDTO);
        bookingDTO.setNumberOfPerson(2);
        bookingDTO.setUser(userDTO);
        bookingDTO.setCheckInDate(Date.valueOf("2023-02-25"));
        bookingDTO.setCheckOutDate(Date.valueOf("2023-02-27"));
        bookingDTO.setPrice(100.0);
        bookingDTO.setDateOfBooking(Date.valueOf("2023-02-20"));
        bookingDTO.setStatusOfBooking(Status.BOOKED);
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        bookingDTOS.add(bookingDTO);
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            bookingService = new BookingService();
            assertEquals(bookingDTOS.toString(), bookingService.getBookingFromRequest(requestDTO, userDTO).toString());
        }
    }
}