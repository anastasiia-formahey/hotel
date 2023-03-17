package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.OccupancyOfRoomDAO;
import com.anastasiia.dao.RoomDAO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.entity.Room;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.exceptions.NoResultException;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
/**
 * RoomService - class mediates communication between a controller and DAO/repository layer
 */
public class RoomService {
    private static final Logger log = Logger.getLogger(RoomService.class);
    private final RoomDAO roomDAO = new RoomDAO(DBManager.getInstance());


    /**
     * @param roomDTO <code>RoomDTO</code> object to insert
     * @return <code>Room</code> identity as int number, 0 - if object was not insert
     */
    public int insertRoom(RoomDTO roomDTO) throws ServiceException {
        try {
            return roomDAO.insertRoom(dtoToEntity(roomDTO));
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            throw new ServiceException(JspAttributes.ROOM_ADD_EXCEPTION);
        }
    }

    /**
     * Method updates the Room object. Gets parameter from the request
     * @param room RoomDTO object to edit
     */
    public void editRoom(RoomDTO room) throws ServiceException {
        try {
            roomDAO.updateRoom(dtoToEntity(room));
            new FeatureService().updateFeatures(dtoToEntity(room));
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            throw new ServiceException(JspAttributes.ROOM_EDIT_EXCEPTION);
        }

    }

    /**
     * Method selects Room objects with limits. This method uses to implement a pagination
     * @param startPage start record to selecting
     * @param amount number of records to select
     * @param orderBy parameter for sorting records, default value = id
     * @return list of <code>RoomDTO</code> objects with certain number of records
     */
    public List<RoomDTO> selectAllRooms(int startPage, int amount, String orderBy) throws ServiceException {
        startPage = startPage * Pagination.RECORDS_PER_PAGE - amount;
        if (orderBy == null){
            orderBy="id";
        }
        try {
            return selectAllRoomsDTO(roomDAO.selectAllRooms(startPage, amount, orderBy));
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            throw new ServiceException(e);
        }
    }

    /**
     * Method generates list of DTO from entities
     * @param rooms list of Room entity objects
     * @return list of RoomDTO objects
     */
    public List<RoomDTO> selectAllRoomsDTO(List<Room> rooms){
        return rooms.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    public List<Room> selectAllRooms() throws ServiceException {
        try {
            return roomDAO.selectAllRooms();
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            throw new ServiceException(e);
        }
    }
    public int countAllRooms(){
        try {
            return roomDAO.countAllRooms();
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            return 0;
        }
    }

    /**
     * Method search free rooms to book on specified dated
     * @param numberOfPerson <code>Room</code> field number of person
     * @param checkIn date to check in
     * @param checkOut date to check out
     * @return list of RoomDTO objects
     */
    public List<RoomDTO> findRoomForBooking(int numberOfPerson, Date checkIn, Date checkOut) throws ServiceException {
        try {
            List<RoomDTO> roomDTOList =
                    roomDAO.selectRoomsForBooking(numberOfPerson, checkIn, checkOut)
                    .stream().map(this::entityToDTO).collect(Collectors.toList());
            if(roomDTOList.isEmpty()) throw new NoResultException(JspAttributes.NO_RESULT_MESSAGE);
            else return roomDTOList;
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            throw new ServiceException(e);
        }
    }

    /**
     * @param id Room identity
     * @return RoomDTO object
     */
    public RoomDTO findRoomById(int id) throws ServiceException {
        try {
            return entityToDTO(roomDAO.findRoomById(id));
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            throw new ServiceException(e);
        }
    }

    /**
     * Method generates RoomDTO object from Room entity objects
     * @param room Room entity object
     * @return RoomDTO object
     */
    public RoomDTO entityToDTO(Room room){
        RoomDTO roomDTO = new RoomDTO.RoomDTOBuilder()
                .withNumberOfPerson(room.getNumberOfPerson())
                .withPrice(room.getPrice())
                .withClassOfRoom(room.getClassOfRoom())
                .withImage(room.getImage())
                .build();
        roomDTO.setId(room.getId());
        return roomDTO;
    }

    /**
     * Method generates Room entity object from RoomDTO object
     * @param roomDTO RoomDTO object
     * @return Room entity object
     */
    public Room dtoToEntity(RoomDTO roomDTO){
        Room room = new Room();
        room.setId(roomDTO.getId());
        room.setNumberOfPerson(roomDTO.getNumberOfPerson());
        room.setPrice(roomDTO.getPrice());
        room.setClassOfRoom(roomDTO.getClassOfRoom());
        room.setImage(roomDTO.getImage());
        room.setFeatures(roomDTO.getFeatures());
        return room;
    }

    /**
     * @param dateOfOccupancy specified date
     * @return HashMap where <b>key</b> - <code>Room</code> identity,
     *      <b>value</b> - <code>Status</code> of <code>Room</code> on specified date
     */
    public Map<Integer, Status> getRoomMap(Date dateOfOccupancy) throws ServiceException {
        try {
            return roomDAO.selectRoomsForMap(dateOfOccupancy);
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            throw new ServiceException(e);
        }
    }
}
