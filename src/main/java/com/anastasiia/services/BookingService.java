package com.anastasiia.services;

import com.anastasiia.dao.BookingDAO;
import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.Booking;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class BookingService {
    private static final Logger log = Logger.getLogger(BookingService.class);
    {
        List<Booking> bookings = BookingDAO.getInstance().selectAll();
        for (Booking booking:bookings) {
            log.debug(withDrawnBooking(booking));
            if(!withDrawnBooking(booking)
                    && booking.getStatusOfBooking().equals(Status.BOOKED)){
                booking.setStatusOfBooking(Status.CANCELED);
                updateStatus(booking);
            }
        }
    }
    public static void insertBooking(Booking booking){
        BookingDAO.getInstance().insertBooking(booking);
    }

    public static List<BookingDTO> selectAllBooking(){
        List<Booking> listOfBooking = BookingDAO.getInstance().selectAll();
        return listOfBooking
                .stream().map(new BookingDTO()::entityToDTO).collect(Collectors.toList());
    }
    public static List<BookingDTO> selectAllBooking(int userId){
        List<Booking> listOfBooking = BookingDAO.getInstance().selectAllByUserId(userId);
        return listOfBooking
                .stream().map(new BookingDTO()::entityToDTO).collect(Collectors.toList());
    }
    public static List<BookingDTO> selectAllBooking(int currentPage, int recordsPerPage, String orderBy){
        currentPage = currentPage * Pagination.RECORDS_PER_PAGE - recordsPerPage;
        List<Booking> listOfBooking = BookingDAO.getInstance().selectAll(currentPage,  recordsPerPage, orderBy);
        return listOfBooking
                .stream().map(new BookingDTO()::entityToDTO).collect(Collectors.toList());
    }
    public static List<BookingDTO> selectAllBooking(int currentPage, int recordsPerPage, String orderBy,int userId){
        currentPage = currentPage * Pagination.RECORDS_PER_PAGE - recordsPerPage;
        List<Booking> listOfBooking = BookingDAO.getInstance().selectAllByUserId( currentPage, recordsPerPage, orderBy,userId);
        return listOfBooking
                .stream().map(new BookingDTO()::entityToDTO).collect(Collectors.toList());
    }
    public static void updateStatus(Booking booking){
        BookingDAO.getInstance().updateStatus(booking);
    }
    public static boolean withDrawnBooking(Booking booking){
       return booking.getBookingExpirationDate()
               .after(BookingService.getCurrentDate());
    }
    public static java.sql.Date getCurrentDate(){
        return new java.sql.Date(Calendar.getInstance().getTime().getTime());
    }

}
