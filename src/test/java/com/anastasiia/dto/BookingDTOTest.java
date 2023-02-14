package com.anastasiia.dto;

import com.anastasiia.entity.Booking;
import com.anastasiia.services.BookingService;
import com.anastasiia.utils.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class BookingDTOTest {
    BookingDTO bookingDTO;
    BookingService bookingService;
    @BeforeEach
    void setUp() {
        bookingService = new BookingService();
        bookingDTO = new BookingDTO();
        bookingDTO.setId(1);
        bookingDTO.setRoomId(1);
        bookingDTO.setRoom(new RoomDTO());
        bookingDTO.setNumberOfPerson(2);
        bookingDTO.setUser(new UserDTO());
        bookingDTO.setCheckInDate(Date.valueOf("2023-02-25"));
        bookingDTO.setCheckOutDate(Date.valueOf("2023-02-26"));
        bookingDTO.setPrice(100.0);
        bookingDTO.setDateOfBooking(bookingService.getCurrentDate());
        bookingDTO.setStatusOfBooking(Status.BOOKED);
    }

    @Test
    void setBookingExpirationDate() {
        bookingDTO.setDateOfBooking(Date.valueOf("2023-02-01"));
        bookingDTO.setBookingExpirationDate();
        assertEquals(Date.valueOf("2023-02-03"), bookingDTO.getBookingExpirationDate());
    }

}