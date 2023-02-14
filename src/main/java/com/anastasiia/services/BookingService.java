package com.anastasiia.services;

import com.anastasiia.dao.BookingDAO;
import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.OccupancyOfRoomDAO;
import com.anastasiia.dao.RoomDAO;
import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.Booking;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BookingService {
    private static final Logger log = Logger.getLogger(BookingService.class);
    private final BookingDAO bookingDAO = new BookingDAO(DBManager.getInstance());
    private final OccupancyOfRoomDAO occupancyOfRoomDAO = new OccupancyOfRoomDAO(DBManager.getInstance());
    private void checkBooking(){
        List<Booking> bookings;
        try {
            bookings = bookingDAO.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Booking booking:bookings) {
            log.debug(withDrawnBooking(booking));
            if(!withDrawnBooking(booking)
                    && booking.getStatusOfBooking().equals(Status.BOOKED)){
                updateStatus(booking.getId(), booking.getRoomId(), Status.CANCELED);
            }
        }
    }
    public boolean insertBooking(List <BookingDTO> bookingDTOS, boolean isConfirm){
        boolean isSuccess = false;
        List<Booking> bookings = bookingDTOS.stream()
                .map(BookingDTO::dtoToEntity)
                .collect(Collectors.toList());

        try {
            isSuccess = isConfirm
                    ? bookingDAO.insertBooking(bookings, true)
                    : bookingDAO.insertBooking(bookings);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (isSuccess){
            for (Booking booking: bookings){
                new OccupancyOfRoomService().insertOccupancyOfRoom(
                        booking.getRoomId(),
                        booking.getClientId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(),
                        Status.BOOKED
                );
            }
        }
        return isSuccess;
    }

    public List<BookingDTO> selectAllBooking(){
        checkBooking();
        List<Booking> listOfBooking = null;
        try {
            listOfBooking = bookingDAO.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfBooking
                .stream().map(new BookingDTO()::entityToDTO).collect(Collectors.toList());
    }
    public List<BookingDTO> selectAllBooking(int userId){
        checkBooking();
        List<Booking> listOfBooking = bookingDAO.selectAllByUserId(userId);
        return listOfBooking
                .stream().map(new BookingDTO()::entityToDTO).collect(Collectors.toList());
    }
    public List<BookingDTO> selectAllBooking(int currentPage, int recordsPerPage, String orderBy){
        checkBooking();
        currentPage = currentPage * Pagination.RECORDS_PER_PAGE - recordsPerPage;
        List<Booking> listOfBooking = bookingDAO.selectAll(currentPage,  recordsPerPage, orderBy);
        return listOfBooking
                .stream().map(new BookingDTO()::entityToDTO).collect(Collectors.toList());
    }
    public List<BookingDTO> selectAllBooking(int currentPage, int recordsPerPage, String orderBy,int userId){
        checkBooking();
        currentPage = currentPage * Pagination.RECORDS_PER_PAGE - recordsPerPage;
        List<Booking> listOfBooking = bookingDAO.selectAllByUserId( currentPage, recordsPerPage, orderBy,userId);
        return listOfBooking
                .stream().map(new BookingDTO()::entityToDTO).collect(Collectors.toList());
    }
    public void updateStatus(int bookingId, int roomId, Status status){
        try {
            bookingDAO.updateStatus(bookingId, status);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        occupancyOfRoomDAO.updateStatus(roomId, status);
    }
    public boolean withDrawnBooking(Booking booking){
       return booking.getBookingExpirationDate()
               .after(getCurrentDate());
    }
    public java.sql.Date getCurrentDate(){
        return new java.sql.Date(Calendar.getInstance().getTime().getTime());
    }
    /**
     * Method that calculates the total cost
     * @param price the price of room by one day
     * @param checkIn the date of check in room
     * @param checkOut the date of check out room
     * */
    public double getTotalCost(double price, Date checkIn, Date checkOut){
        return ChronoUnit.DAYS.between(LocalDate.parse(checkIn.toString()), LocalDate.parse(checkOut.toString())) * price;
    }
    public List<BookingDTO> getBookingFromRequest(RequestDTO requestDTO, UserDTO userDTO){
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        for (RequestDTO.RequestElement requestElement: requestDTO.getRequestElements()) {
            BookingDTO bookingDTO = new BookingDTO(
                  requestElement.getRoom(),
                  userDTO,
                  requestElement.getCheckInDate(),
                  requestElement.getCheckOutDate(),
                  getTotalCost(
                            requestElement.getRoom().getPrice(),
                            requestElement.getCheckInDate(),
                            requestElement.getCheckOutDate()
                  ),
                  getCurrentDate()
            );
            bookingDTO.setBookingExpirationDate();
            bookingDTOS.add(bookingDTO);
        }
        return bookingDTOS;
    }

}
