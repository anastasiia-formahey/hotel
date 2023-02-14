package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.OccupancyOfRoomDAO;
import com.anastasiia.utils.Status;

import java.sql.Date;

public class OccupancyOfRoomService {
    private final OccupancyOfRoomDAO occupancyOfRoomDAO = new OccupancyOfRoomDAO(DBManager.getInstance());

    public void insertOccupancyOfRoom(int roomId, int clientId, Date checkIn, Date checkOut, Status status){
        if(occupancyOfRoomDAO
                .isExist(roomId, clientId, checkIn, checkOut, status)){
            occupancyOfRoomDAO.updateStatus(roomId, status);
        }else {
            occupancyOfRoomDAO.insertOccupancyOfRoom(roomId, clientId, checkIn, checkOut, status);
        }
       }
}
