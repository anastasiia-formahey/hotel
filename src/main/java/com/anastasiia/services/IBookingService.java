package com.anastasiia.services;

import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.Booking;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.utils.Status;

import java.util.Date;
import java.util.List;

/**
 * The interface implements behavior for BookingService.
 */
public interface IBookingService {
    /**
     * Method generates BookingDTO object from Booking entity object
     * @param booking Booking entity object
     * @return BookingDTO object
     */
    BookingDTO entityToDTO(Booking booking);

    /**
     * Method generates Booking entity object from BookingDTO object
     * @param bookingDTO BookingDTO object
     * @return Booking entity object
     */
    Booking dtoToEntity(BookingDTO bookingDTO);

    /**
     * Method checks Bookings by Status.
     * If Booking was not <code>PAID</code> within two days from creation,
     * Status changes to <code>CANCELED</code>
     */
    void checkBooking();

    /**
     *Method inserts new Booking object into table
     * @param bookingDTOS list of BookingDTO objects to insert
     * @param isConfirm <b>true</b> if inserted bookings are confirmation of request on application
     *                  <p><b>false</b> if inserted bookings has created by client
     * @return true - if objects were inserted
     */
    boolean insertBooking(List<BookingDTO> bookingDTOS, boolean isConfirm) throws ServiceException;

    /**
     * Method selects all Booking objects from the table with limits.
     * This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param recordsPerPage number of records to select
     * @param orderBy parameter for sorting records
     * @return list of BookingDTO objects with certain number of records
     */
    List<BookingDTO> selectAllBooking(int currentPage, int recordsPerPage, String orderBy) throws ServiceException;

    /**
     * Method selects all Booking objects from the table with limits by user identity.
     *This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param recordsPerPage number of records to select
     * @param orderBy parameter for sorting records
     * @param userId User identity
     * @return list of BookingDTO objects with certain number of records by specified User
     */
    List<BookingDTO> selectAllBooking(int currentPage, int recordsPerPage, String orderBy,int userId) throws ServiceException;

    /**
     * Method updates status of Booking object and OccupancyOfRoom object
     * @param bookingId Booking object identity
     * @param roomId Room object identity
     * @param checkIn the date checking in
     * @param checkOut the date checking out
     * @param status Status object to update (<code>BOOKED, PAID, BUSY, CANCELED</code>)
     */
    void updateStatus(int bookingId, int roomId, java.sql.Date checkIn, java.sql.Date checkOut, Status status) throws ServiceException;

    /**
     * Method checks relevance of the booking on current date
     * @param booking Booking object to check
     * @return <b>true</b> if current date  is before booking expiration date
     */
    boolean withDrawnBooking(Booking booking);

    /**
     * Method that calculates the total cost
     * @param price the price of room by one day
     * @param checkIn the date of check in room
     * @param checkOut the date of check out room
     * */
    double getTotalCost(double price, Date checkIn, Date checkOut);

    /**
     * Method takes BookingDTO objects from the RequestDTO object
     * @param requestDTO RequestDTO object
     * @param userDTO UserDTO object
     * @return list of BookingDTO objects
     */
    List<BookingDTO> getBookingFromRequest(RequestDTO requestDTO, UserDTO userDTO);

    /**
     * Method counts common amount of records
     * @return common amount of records
     */
    int countAllBooking();

    /**
     * Method counts common amount of records by specified user
     * @param userId User identity
     * @return common amount of records by specified user
     */
    int countAllBookingByUserId(int userId);
}
