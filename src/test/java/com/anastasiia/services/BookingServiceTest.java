package com.anastasiia.services;

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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    BookingService bookingService;
    Booking booking;
    RequestDTO requestDTO;
    UserDTO userDTO;
    RoomDTO roomDTO;
    @BeforeEach
    void setUp() {
        bookingService = new BookingService();
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

    @AfterEach
    void tearDown() {
        bookingService = null;
        booking = null;
        requestDTO=null;
        userDTO=null;
        roomDTO=null;
    }

    @Test
    void withDrawnBooking() {
        assertFalse(bookingService.withDrawnBooking(booking));
        booking.setDateOfBooking(Date.valueOf("2023-02-20"));
        booking.setBookingExpirationDate();
    }

    @Test
    void getTotalCost() {
        assertEquals(2000.0,
                bookingService.getTotalCost(
                        1000.0,
                        Date.valueOf("2023-02-02"),
                        Date.valueOf("2023-02-04")));
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
        bookingDTO.setDateOfBooking(bookingService.getCurrentDate());
        bookingDTO.setStatusOfBooking(Status.BOOKED);
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        bookingDTOS.add(bookingDTO);
        assertEquals(bookingDTOS.toString(), bookingService.getBookingFromRequest(requestDTO,userDTO).toString());
    }
}