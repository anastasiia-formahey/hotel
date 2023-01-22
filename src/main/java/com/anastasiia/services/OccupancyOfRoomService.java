package com.anastasiia.services;

import com.anastasiia.dao.OccupancyOfRoomDAO;
import com.anastasiia.utils.Status;

import java.sql.Date;

public class OccupancyOfRoomService {

    public void insertOccupancyOfRoom(int roomId, int clientId, Date checkIn, Date checkOut, Status status){
        if(OccupancyOfRoomDAO.getInstance()
                .isExist(roomId, clientId, checkIn, checkOut, status)){
            OccupancyOfRoomDAO.getInstance().updateStatus(roomId, status);
        }else {
            OccupancyOfRoomDAO.getInstance().insertOccupancyOfRoom(roomId, clientId, checkIn, checkOut, status);
        }
       }
}
