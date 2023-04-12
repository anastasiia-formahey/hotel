package com.anastasiia.services.impl;

import com.anastasiia.dao.*;
import com.anastasiia.dao.impl.BookingDAO;
import com.anastasiia.dao.impl.OccupancyOfRoomDAO;
import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.Booking;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.services.*;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BookingService - class mediates communication between a controller and DAO/repository layer
 */
public class BookingService implements IBookingService {
    private static final Logger log = Logger.getLogger(BookingService.class);

    private final BookingDAO bookingDAO = new BookingDAO(DBManager.getInstance());
    private final OccupancyOfRoomDAO occupancyOfRoomDAO = new OccupancyOfRoomDAO(DBManager.getInstance());
   private final UserService userService = new UserService();

    /**
     * Method generates BookingDTO object from Booking entity object
     * @param booking Booking entity object
     * @return BookingDTO object
     */
    public BookingDTO entityToDTO(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();
        try {
            bookingDTO.setId(booking.getId());
            bookingDTO.setRoomId(booking.getRoomId());
            bookingDTO.setNumberOfPerson(new RoomService()
                    .findRoomById(booking.getRoomId())
                    .getNumberOfPerson());
            bookingDTO.setUser(new UserService().getUser(booking.getClientId()));
            bookingDTO.setCheckInDate(booking.getCheckInDate());
            bookingDTO.setCheckOutDate(booking.getCheckOutDate());
            bookingDTO.setPrice(booking.getPrice());
            bookingDTO.setDateOfBooking(booking.getDateOfBooking());
            bookingDTO.setStatusOfBooking(booking.getStatusOfBooking());
            bookingDTO.setBookingExpirationDate(booking.getBookingExpirationDate());
        } catch (ServiceException e) {
            log.error("ServiceException was caught. Cause : "+ e);
        }

        return bookingDTO;
    }

    /**
     * Method generates Booking entity object from BookingDTO object
     * @param bookingDTO BookingDTO object
     * @return Booking entity object
     */
    public Booking dtoToEntity(BookingDTO bookingDTO){
        Booking booking = new Booking();

        try {
            booking.setId(bookingDTO.getId());
            booking.setRoomId(bookingDTO.getRoom().getId());
            booking.setClientId(userService.dtoToEntity(bookingDTO.getUser()).getId());
            booking.setCheckInDate(bookingDTO.getCheckInDate());
            booking.setCheckOutDate(bookingDTO.getCheckOutDate());
            booking.setPrice(bookingDTO.getPrice());
            booking.setDateOfBooking(bookingDTO.getDateOfBooking());
            booking.setStatusOfBooking(bookingDTO.getStatusOfBooking());
            booking.setBookingExpirationDate();
        }catch (ServiceException e){
            log.error("ServiceException was caught. Cause : "+ e);
        }
        return booking;
    }

    /**
     * Method checks Bookings by Status.
     * If Booking was not <code>PAID</code> within two days from creation,
     * Status changes to <code>CANCELED</code>
     */
    public void checkBooking(){
        List<Booking> bookings;
        try {
            bookings = bookingDAO.selectAll();
            for (Booking booking:bookings) {
                if(!withDrawnBooking(booking)
                        && booking.getStatusOfBooking().equals(Status.BOOKED)
                        || !withDrawnBooking(booking)
                        && booking.getStatusOfBooking().equals(Status.NOT_CONFIRMED)){
                    updateStatus(booking.getId(), booking.getRoomId(),
                            booking.getCheckInDate(), booking.getCheckOutDate(), Status.CANCELED);
                }

            }
        } catch (ServiceException | DAOException e) {
            log.error("Exception was caught. Cause : "+ e);
        }

    }

    /**
     *Method inserts new Booking object into table
     * @param bookingDTOS list of BookingDTO objects to insert
     * @param isConfirm <b>true</b> if inserted bookings are confirmation of request on application
     *                  <p><b>false</b> if inserted bookings has created by client
     * @return true - if objects were inserted
     */
    public boolean insertBooking(List <BookingDTO> bookingDTOS, boolean isConfirm) throws ServiceException {
        boolean isSuccess = false;
        List<Booking> bookings = bookingDTOS.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
        try {
            isSuccess = isConfirm
                    ? bookingDAO.insertBooking(bookings, true)
                    : bookingDAO.insertBooking(bookings);
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
            } else throw new ServiceException(JspAttributes.BOOKING_EXIST);
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
        }
        return isSuccess;
    }

    /**
     * Method selects all Booking objects from the table with limits.
     * This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param recordsPerPage number of records to select
     * @param orderBy parameter for sorting records
     * @return list of BookingDTO objects with certain number of records
     */
    public List<BookingDTO> selectAllBooking(int currentPage, int recordsPerPage, String orderBy) throws ServiceException {
        checkBooking();
        currentPage = currentPage * Pagination.RECORDS_PER_PAGE - recordsPerPage;
        List<Booking> listOfBooking = null;
        try {
            listOfBooking = bookingDAO.selectAll(currentPage,  recordsPerPage, orderBy);
            return listOfBooking
                    .stream().map(this::entityToDTO).collect(Collectors.toList());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    /**
     * Method selects all Booking objects from the table with limits by user identity.
     *This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param recordsPerPage number of records to select
     * @param orderBy parameter for sorting records
     * @param userId User identity
     * @return list of BookingDTO objects with certain number of records by specified User
     */
    public List<BookingDTO> selectAllBooking(int currentPage, int recordsPerPage, String orderBy,int userId) throws ServiceException {
        checkBooking();
        currentPage = currentPage * Pagination.RECORDS_PER_PAGE - recordsPerPage;
        List<Booking> listOfBooking = null;
        try {
            listOfBooking = bookingDAO.selectAllByUserId( currentPage, recordsPerPage, orderBy,userId);
            return listOfBooking
                    .stream().map(this::entityToDTO).collect(Collectors.toList());
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            throw new ServiceException(e);
        }

    }

    /**
     * Method updates status of Booking object and OccupancyOfRoom object
     * @param bookingId Booking object identity
     * @param roomId Room object identity
     * @param checkIn the date checking in
     * @param checkOut the date checking out
     * @param status Status object to update (<code>BOOKED, PAID, BUSY, CANCELED</code>)
     */
    public void updateStatus(int bookingId, int roomId, java.sql.Date checkIn, java.sql.Date checkOut, Status status) throws ServiceException {
        try {
            bookingDAO.updateStatus(bookingId, status);
            occupancyOfRoomDAO.updateStatus(roomId,status, checkIn,checkOut);
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            throw new ServiceException(e);
        }

    }

    /**
     * Method checks relevance of the booking on current date
     * @param booking Booking object to check
     * @return <b>true</b> if current date  is before booking expiration date
     */
    public boolean withDrawnBooking(Booking booking){
       return booking.getBookingExpirationDate()
               .after(getCurrentDate());
    }

    /**
     * @return The current date
     */
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

    /**
     * Method takes BookingDTO objects from the RequestDTO object
     * @param requestDTO RequestDTO object
     * @param userDTO UserDTO object
     * @return list of BookingDTO objects
     */
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
    /**
     * Method counts common amount of records
     * @return common amount of records
     */
    public int countAllBooking(){
        try{
            return bookingDAO.countAllBooking();
        }catch (DAOException e){
            log.error("DAOException was caught. Cause : "+ e);
            return 0;
        }
    }
    /**
     * Method counts common amount of records by specified user
     * @param userId User identity
     * @return common amount of records by specified user
     */
    public int countAllBookingByUserId(int userId){
        try{
            return bookingDAO.countAllBooking(userId);
        }catch (DAOException e){
            log.error("DAOException was caught. Cause : "+ e);
            return 0;
        }
    }

}
