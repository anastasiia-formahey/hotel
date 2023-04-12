package com.anastasiia.dao;

import com.anastasiia.entity.Room;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.utils.Status;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Interface that implements behavior for RoomDAO
 */
public interface IRoomDAO {
    /**
     * Method that insert a new record into the table
     * @param room <code>Room</code> object to insert
     * @return <code>Room</code> identity as int number, 0 - if object was not insert
     */
    int insertRoom(Room room) throws DAOException;
    /**
     * Method selects all records from the table
     * @return list of <code>Room</code> objects
     */
    List<Room> selectAllRooms() throws DAOException;

    /**
     * Method selects records from the table by specified date
     * @param date <code>sql.Data</code> object
     * @return HashMap where <b>key</b> - <code>Room</code> identity,
     * <b>value</b> - <code>Status</code> of <code>Room</code> on specified date
     */
    Map<Integer, Status> selectRoomsForMap(Date date) throws DAOException;

    /**
     * Method selects <code>Room</code> object by identity
     * @param id <code>Room</code> identity
     * @return <code>Room</code> object
     */
    Room findRoomById(int id) throws DAOException;

    /**
     * Method updates the record in the table
     * @param room <code>Room</code> object to update
     */
    void updateRoom(Room room) throws DAOException;

    /**
     * Method selects all records from the table with limits. This method uses to implement a pagination
     * @param startPage start record to selecting
     * @param amount number of records to select
     * @param orderBy parameter for sorting records
     * @return list of <code>Room</code> objects with certain number of records
     */
    List<Room> selectAllRooms(int startPage, int amount, String orderBy) throws DAOException;

    /**
     * Method selects all records from the table with specified parameters
     * @param numberOfPerson <code>Room</code> field number of person
     * @param checkInDate date to check in
     * @param checkOutDate date to check out
     * @return list of <code>Room</code> objects that can be booked on the dates indicated
     */
    List<Room> selectRoomsForBooking(int numberOfPerson,Date checkInDate,Date checkOutDate) throws DAOException;

    /**
     * Method counts common amount of records
     * @return common amount of records
     */
    int countAllRooms() throws DAOException;

}
