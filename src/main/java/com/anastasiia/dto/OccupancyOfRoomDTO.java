package com.anastasiia.dto;

import com.anastasiia.utils.Status;

import java.sql.Date;
/**
 * <code>OccupancyOfRoomDTO</code> - class implements data transfer object for <code>OccupancyOfRoom entity</code>
 */
public class OccupancyOfRoomDTO {
    private int roomId;
    private UserDTO client;
    private Date checkInDate;
    private Date checkOutDate;
    private Status status;

    public OccupancyOfRoomDTO(int roomId, UserDTO client, Date checkInDate, Date checkOutDate, Status status) {
        this.roomId = roomId;
        this.client = client;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
    }

    public int getRoomId() {
        return roomId;
    }

    public UserDTO getClient() {
        return client;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public Status getStatus() {
        return status;
    }

    public OccupancyOfRoomDTO updateStatus(Status status) {
        this.status = status;
        return this;
    }
}
