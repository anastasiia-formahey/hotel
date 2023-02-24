package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.OccupancyOfRoomDAO;
import com.anastasiia.dao.RoomDAO;
import com.anastasiia.dto.BookingDTO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.entity.Room;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Fields;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
/**
 * RoomService - class mediates communication between a controller and DAO/repository layer
 */
public class RoomService {
    private static final Logger log = Logger.getLogger(RoomService.class);
    private final RoomDAO roomDAO = new RoomDAO(DBManager.getInstance());
    private final OccupancyOfRoomDAO occupancyOfRoomDAO = new OccupancyOfRoomDAO(DBManager.getInstance());

    public List<Integer> getCapacityOfRoom(){
        //todo delete this method
        ArrayList <Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(6);
        list.add(8);
        return list;
    }

    /**
     * @param roomDTO <code>RoomDTO</code> object to insert
     * @return <code>Room</code> identity as int number, 0 - if object was not insert
     */
    public int insertRoom(RoomDTO roomDTO){
       return roomDAO.insertRoom(dtoToEntity(roomDTO));
    }

    /**
     * Method updates the Room object. Gets parameter from the request
     * @param request HttpServletRequest request
     */
    public void editRoom(HttpServletRequest request){
        RoomDTO room = new RoomDTO();
        RoomDTO roomEdit = (RoomDTO) request.getSession().getAttribute("roomEdit");
        room.setNumberOfPerson(Integer.parseInt(request.getParameter("numberOfPerson")));
        room.setPrice(Double.parseDouble(request.getParameter("price")));
        room.setClassOfRoom(ClassOfRoom.valueOf(request.getParameter("classOfRoom")));
        String image = request.getParameter("image");
        if(image.isEmpty() || image.equals(" ")){
            image = roomEdit.getImage();
        }
        room.setImage(image);
        room.setId(roomEdit.getId());
        room.setFeatures(new FeatureService().getFeaturesForRoom(request));
        roomDAO.updateRoom(dtoToEntity(room));
        new FeatureService().updateFeatures(dtoToEntity(room));
    }

    /**
     * Method selects Room objects with limits. This method uses to implement a pagination
     * @param startPage start record to selecting
     * @param amount number of records to select
     * @param orderBy parameter for sorting records, default value = id
     * @return list of <code>RoomDTO</code> objects with certain number of records
     */
    public List<RoomDTO> selectAllRooms(int startPage, int amount, String orderBy){
        startPage = startPage * Pagination.RECORDS_PER_PAGE - amount;
        if (orderBy == null){
            orderBy="id";
        }
        return selectAllRoomsDTO(roomDAO.selectAllRooms(startPage, amount, orderBy));
    }

    /**
     * Method generates list of DTO from entities
     * @param rooms list of Room entity objects
     * @return list of RoomDTO objects
     */
    public List<RoomDTO> selectAllRoomsDTO(List<Room> rooms){
        return rooms.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    public List<Room> selectAllRooms(){
        return roomDAO.selectAllRooms();
    }

    /**
     * Method search free rooms to book on specified dated
     * @param request HttpServletRequest request
     * @return list of RoomDTO objects
     */
    public List<RoomDTO> findRoomForBooking(HttpServletRequest request){
        return roomDAO.selectRoomsForBooking(
                Integer.parseInt(request.getParameter(JspAttributes.NUMBER_OF_PERSONS)),
                Date.valueOf(request.getParameter(JspAttributes.CHECK_IN_DATE)),
                Date.valueOf(request.getParameter(JspAttributes.CHECK_OUT_DATE)))
                .stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    /**
     * @param id Room identity
     * @return RoomDTO object
     */
    public RoomDTO findRoomById(int id){
        return entityToDTO(roomDAO.findRoomById(id));
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
    public Map<Integer, Status> getRoomMap(Date dateOfOccupancy) {
        return roomDAO.selectRoomsForMap(dateOfOccupancy);
    }
}
