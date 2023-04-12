package com.anastasiia.dao;

import com.anastasiia.entity.Booking;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.utils.Status;

import java.util.List;

/**
 * Interface that implements behavior for BookingDAO
 */
public interface IBookingDAO {
    /**
     * Method inserts new object into table
     * @param bookings list of objects <code>Booking.class</code> to insert
     * @return true - if objects were inserted
     * @throws DAOException
     */
    boolean insertBooking(List<Booking> bookings) throws DAOException;

    /**
     * Method inserts new object into table
     * @param bookings list of objects <code>Booking.class</code> to insert
     * @param isConfirm <b>true</b> if inserted bookings are confirmation of request on application
     * @return true - if objects were inserted
     * @throws DAOException
     */
    boolean insertBooking(List<Booking> bookings, boolean isConfirm) throws DAOException;

    /**
     * Method updates status of a record in table
     * @param bookingId booking identity
     * @param status booking status to update
     * @throws DAOException
     */
    void updateStatus(int bookingId, Status status) throws DAOException;

    /**
     * Method selects all records from the table
     * @return list of objects <code>Booking.class</code>
     * @throws DAOException
     */
    List<Booking> selectAll() throws DAOException;

    /**
     * Method selects all records from the table with limits. This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param amount number of records to select
     * @param orderBy parameter for sorting records
     * @return list of objects <code>Booking.class</code> with certain number of records
     */
    List<Booking> selectAll(int currentPage, int amount, String orderBy) throws DAOException;

    /**
     * Method selects all records from the table by user identity
     * @param userId user identity
     * @return list of objects <code>Booking.class</code> by specific user
     */
    List<Booking> selectAllByUserId(int userId) throws DAOException;

    /**
     * Method selects all records from the table with limits. This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param amount number of records to select
     * @param orderBy parameter for sorting records
     * @param userId user identity
     * @return list of objects <code>Booking.class</code> with certain number of records by specific user
     */
    List<Booking> selectAllByUserId(int currentPage, int amount, String orderBy,int userId) throws DAOException;

    /**
     * Method counts common amount of records
     * @return common amount of records
     */
    int countAllBooking() throws DAOException;

    /**
     * Method counts common amount of records by specified user
     * @param userId User identity
     * @return common amount of records by specified user
     */
    int countAllBooking(int userId) throws DAOException;

}
