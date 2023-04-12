package com.anastasiia.dao;

import com.anastasiia.entity.OccupancyOfRoom;
import com.anastasiia.entity.User;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.utils.Status;

import java.sql.Date;
import java.util.Map;

/**
 * Interface that implements behavior for OccupancyOfRoomDAO
 */
public interface IOccupancyOfRoomDAO {

    /**
     * Method inserts new record occupancy of room into table
     * @param roomId <code>Room</code> identity
     * @param clientId <code>User</code> identity
     * @param checkIn the date checking in
     * @param checkOut the date checking out
     * @param status <code>Status</code> object
     */
    void insertOccupancyOfRoom(int roomId, int clientId, Date checkIn, Date checkOut, Status status) throws DAOException;

    /**
     * Method updates status an occupancy of room by <code>Room</code> identity and dates checking in/out
     * @param roomId <code>Room</code> identity
     * @param status <code>Status</code> object (<tt>NOT_CONFIRMED</tt>, <tt>BOOKED</tt>, <tt>PAID</tt>,
     *      <tt>BUSY</tt>, <tt>CANCELED</tt>)
     * @param checkIn the date checking in
     * @param checkOut the date checking out
     */
    void updateStatus(int roomId, Status status, Date checkIn, Date checkOut) throws DAOException;

    /**
     * Method checks for the existence of a record by <code>Room</code> identity,
     * <code>User</code> identity and dates checking in/out
     *
     * @param roomId <code>Room</code> identity
     * @param clientId <code>User</code> identity
     * @param checkIn the date checking in
     * @param checkOut the date checking out
     * @return <b>true</b> - if the record exists according to the given parameters,
     * <p><b>false</b> - if the record does not exist</p>
     */
    boolean isExist(int roomId, int clientId, Date checkIn, Date checkOut) throws DAOException;

    /**
     * Method select <code>Status</code> by specific <code>Room</code>
     * from the table on the specified date
     *
     * @param roomId <code>Room</code> identity
     * @param date the date to know status
     * @return <code>Status</code> object (<tt>NOT_CONFIRMED</tt>, <tt>BOOKED</tt>, <tt>PAID</tt>,
     * <tt>BUSY</tt>, <tt>CANCELED</tt>)
     */
    Status getStatus(int roomId, Date date) throws DAOException;

    /**
     * Method selects all records by specific <code>Room</code>
     *
     * @param roomId <code>Room</code> identity
     * @param date the date to select
     * @return HashMap <<code>OccupancyOfRoom</code>, <code>User</code>> - map
     * <code>OccupancyOfRoom</code> objects by specific <code>User</code>
     */
    Map<OccupancyOfRoom, User> selectByRoomId(int roomId, Date date) throws DAOException;
}
