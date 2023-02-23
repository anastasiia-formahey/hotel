package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.OccupancyOfRoomDAO;
import com.anastasiia.dto.OccupancyOfRoomDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.OccupancyOfRoom;
import com.anastasiia.entity.User;
import com.anastasiia.utils.Status;

import java.sql.Date;
import java.util.Map;

public class OccupancyOfRoomService {
    private final OccupancyOfRoomDAO occupancyOfRoomDAO = new OccupancyOfRoomDAO(DBManager.getInstance());

    public void insertOccupancyOfRoom(int roomId, int clientId, Date checkIn, Date checkOut, Status status){
        if(occupancyOfRoomDAO
                .isExist(roomId, clientId, checkIn, checkOut)){
            occupancyOfRoomDAO.updateStatus(roomId, status, checkIn,checkOut);
        }else {
            occupancyOfRoomDAO.insertOccupancyOfRoom(roomId, clientId, checkIn, checkOut, status);
        }
       }
       public OccupancyOfRoomDTO getOccupancyOfRoomByRoomId(int roomId, Date date){
           Map<OccupancyOfRoom, User> occupancyOfRoomUserMap = occupancyOfRoomDAO.selectByRoomId(roomId,date);
           OccupancyOfRoomDTO occupancyOfRoomDTO = null;
           for (Map.Entry<OccupancyOfRoom, User> entry: occupancyOfRoomUserMap.entrySet()) {
               OccupancyOfRoom occupancyOfRoom = entry.getKey();
               User user = entry.getValue();
               occupancyOfRoomDTO = new OccupancyOfRoomDTO(
                       occupancyOfRoom.getRoomId(),
                       new UserDTO().entityToDTO(user),
                       occupancyOfRoom.getCheckInDate(),
                       occupancyOfRoom.getCheckOutDate(),
                       occupancyOfRoom.getStatus()
               );
           }
           return occupancyOfRoomDTO;
       }

       public void updateStatus(int roomId, Status status, Date checkIn, Date checkOut){
        occupancyOfRoomDAO.updateStatus(roomId, status, checkIn, checkOut);
       }
}
