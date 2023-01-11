package com.anastasiia.services;

import com.anastasiia.dao.BookingDAO;
import com.anastasiia.dao.RoomDAO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.entity.Booking;
import com.anastasiia.entity.Room;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

    public void editRoom(HttpServletRequest request){
        Room room = new Room();
        Room roomEdit = (Room)request.getSession().getAttribute("roomEdit");
        room.setNumberOfPerson(Integer.parseInt(request.getParameter("numberOfPerson")));
        room.setPrice(Double.parseDouble(request.getParameter("price")));
        room.setClassOfRoom(ClassOfRoom.valueOf(request.getParameter("classOfRoom")));
        String image = request.getParameter("image");
        if(image.isEmpty() || image.equals(" ")){
            image = roomEdit.getImage();
        }
        room.setImage(image);
        room.setId(roomEdit.getId());
        room.setFeatures(FeatureService.getFeaturesForRoom(request));
        RoomDAO.getInstance().updateRoom(room);
        FeatureService.updateFeatures(room);
    }

    public List<Room> selectAllRooms(int startPage, int amount, String orderBy){
        startPage = startPage * Pagination.RECORDS_PER_PAGE - amount;
        if (orderBy == null){
            orderBy="id";
        }
        return RoomDAO.getInstance().selectAllRooms(startPage, amount, orderBy);
    }

    public List<Room> findRoomForBooking(HttpServletRequest request){
        return RoomDAO.getInstance().selectRoomsForBooking(
                Integer.parseInt(request.getParameter(JspAttributes.NUMBER_OF_PERSONS)),
                Date.valueOf(request.getParameter(JspAttributes.CHECK_IN_DATE)),
                Date.valueOf(request.getParameter(JspAttributes.CHECK_OUT_DATE)));
    }
    private boolean isDateInRange(Date date, Booking booking) {
        return date.before(booking.getCheckInDate())
                && date.after(booking.getCheckOutDate());
    }

    public Room findRoomById(int id){
        return RoomDAO.getInstance().findRoomById(id);
    }

}
