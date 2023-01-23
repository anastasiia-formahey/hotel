package com.anastasiia.services;

import com.anastasiia.dao.OccupancyOfRoomDAO;
import com.anastasiia.dao.RoomDAO;
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

public class RoomService {
    private static final Logger log = Logger.getLogger(RoomService.class);

    public List<Integer> getCapacityOfRoom(){
        ArrayList <Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(6);
        list.add(8);
        return list;
    }
    public void insertRoom(RoomDTO roomDTO){
        RoomDAO.getInstance().insertRoom(dtoToEntity(roomDTO));
    }

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
        RoomDAO.getInstance().updateRoom(dtoToEntity(room));
        new FeatureService().updateFeatures(dtoToEntity(room));
    }

    public List<RoomDTO> selectAllRooms(int startPage, int amount, String orderBy){
        startPage = startPage * Pagination.RECORDS_PER_PAGE - amount;
        if (orderBy == null){
            orderBy="id";
        }
        return selectAllRoomsDTO(RoomDAO.getInstance().selectAllRooms(startPage, amount, orderBy));
    }

    public List<RoomDTO> selectAllRoomsDTO(List<Room> rooms){
        return rooms.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    public List<RoomDTO> findRoomForBooking(HttpServletRequest request){
        log.debug("number of person = " + request.getParameter(JspAttributes.NUMBER_OF_PERSONS));
        log.debug("in  = " + request.getParameter(JspAttributes.CHECK_IN_DATE));
        log.debug("out  = " + request.getParameter(JspAttributes.CHECK_OUT_DATE));
        return RoomDAO.getInstance().selectRoomsForBooking(
                Integer.parseInt(request.getParameter(JspAttributes.NUMBER_OF_PERSONS)),
                Date.valueOf(request.getParameter(JspAttributes.CHECK_IN_DATE)),
                Date.valueOf(request.getParameter(JspAttributes.CHECK_OUT_DATE)))
                .stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    public RoomDTO findRoomById(int id){
        return entityToDTO(RoomDAO.getInstance().findRoomById(id));
    }
    public RoomDTO entityToDTO(Room room){
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setNumberOfPerson(room.getNumberOfPerson());
        roomDTO.setPrice(room.getPrice());
        roomDTO.setClassOfRoom(room.getClassOfRoom());
        roomDTO.setImage(room.getImage());
        roomDTO.setFeatures(room.getFeatures());
        roomDTO.setStatus(getStatusOfRoom(room));
        return roomDTO;
    }
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

    private Status getStatusOfRoom(Room room) {
        Status status = OccupancyOfRoomDAO.getInstance()
                .getStatus(room, new BookingService().getCurrentDate());
        if (status.equals(Status.PAID)){
            status = Status.BUSY;
        }
//        else if(status.equals(Status.NOT_CONFIRMED)){
//            status = Status.BOOKED;
//        }
        log.debug(status);
        log.debug(room.getId());
        return status;
    }

}
