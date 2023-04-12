package com.anastasiia.services;

import com.anastasiia.dto.OccupancyOfRoomDTO;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.utils.Status;

import java.sql.Date;

/**
 * The interface implements behavior for FeatureService.
 */
public interface IOccupancyOfRoomService {

    /**
     *
     * @param roomId <code>Room</code> identity
     * @param clientId <code>User</code> identity
     * @param checkIn the date checking in
     * @param checkOut the date checking out
     * @param status <code>Status</code> object (<tt>NOT_CONFIRMED</tt>, <tt>BOOKED</tt>, <tt>PAID</tt>,
     *     <tt>BUSY</tt>, <tt>CANCELED</tt>)
     */
    void insertOccupancyOfRoom(int roomId, int clientId, Date checkIn, Date checkOut, Status status);

    /**
     * Method selects all OccupancyOfRoom entity object by specific <code>Room</code>
     * @param roomId <code>Room</code> identity
     * @param date the date to select
     * @return OccupancyOfRoomDTO object
     */
    OccupancyOfRoomDTO getOccupancyOfRoomByRoomId(int roomId, Date date) throws ServiceException;

    /**
     * Method updates status an occupancy of room by <code>Room</code> identity and dates checking in/out
     * @param roomId <code>Room</code> identity
     * @param status <code>Status</code> object
     * @param checkIn the date checking in
     * @param checkOut the date checking out
     */
    void updateStatus(int roomId, Status status, Date checkIn, Date checkOut) throws ServiceException;
}
