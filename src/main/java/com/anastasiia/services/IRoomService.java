package com.anastasiia.services;

import com.anastasiia.dto.RoomDTO;
import com.anastasiia.entity.Room;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.utils.Status;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * The interface implements behavior for RoomService.
 */
public interface IRoomService {
    /**
     * @param roomDTO <code>RoomDTO</code> object to insert
     * @return <code>Room</code> identity as int number, 0 - if object was not insert
     */
    int insertRoom(RoomDTO roomDTO) throws ServiceException;

    /**
     * Method updates the Room object. Gets parameter from the request
     * @param room RoomDTO object to edit
     */
    void editRoom(RoomDTO room) throws ServiceException;

    /**
     * Method selects Room objects with limits. This method uses to implement a pagination
     * @param startPage start record to selecting
     * @param amount number of records to select
     * @param orderBy parameter for sorting records, default value = id
     * @return list of <code>RoomDTO</code> objects with certain number of records
     */
    List<RoomDTO> selectAllRooms(int startPage, int amount, String orderBy) throws ServiceException;

    /**
     * Method generates list of DTO from entities
     * @param rooms list of Room entity objects
     * @return list of RoomDTO objects
     */
    List<RoomDTO> selectAllRoomsDTO(List<Room> rooms);
    List<Room> selectAllRooms() throws ServiceException;
    int countAllRooms();

    /**
     * Method search free rooms to book on specified dated
     * @param numberOfPerson <code>Room</code> field number of person
     * @param checkIn date to check in
     * @param checkOut date to check out
     * @return list of RoomDTO objects
     */
    List<RoomDTO> findRoomForBooking(int numberOfPerson, Date checkIn, Date checkOut) throws ServiceException;

    /**
     * @param id Room identity
     * @return RoomDTO object
     */
    RoomDTO findRoomById(int id) throws ServiceException;
    /**
     * Method generates RoomDTO object from Room entity objects
     * @param room Room entity object
     * @return RoomDTO object
     */
    RoomDTO entityToDTO(Room room);
    /**
     * Method generates Room entity object from RoomDTO object
     * @param roomDTO RoomDTO object
     * @return Room entity object
     */
    Room dtoToEntity(RoomDTO roomDTO);

    /**
     * @param dateOfOccupancy specified date
     * @return HashMap where <b>key</b> - <code>Room</code> identity,
     *      <b>value</b> - <code>Status</code> of <code>Room</code> on specified date
     */
    Map<Integer, Status> getRoomMap(Date dateOfOccupancy) throws ServiceException;

}
