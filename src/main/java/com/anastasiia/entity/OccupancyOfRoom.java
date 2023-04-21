package com.anastasiia.entity;

import com.anastasiia.utils.Status;

import java.io.Serializable;
import java.sql.Date;
/**
 * <code>OccupancyOfRoom</code> - class entity for table 'occupancy_of_room'
 */
public class OccupancyOfRoom implements Serializable {
    private static final long serialVersionUID = -2477584056112270848L;
    private int roomId;
    private int clientId;
    private Date checkInDate;
    private Date checkOutDate;
    private Status status;

    public OccupancyOfRoom(int roomId, int clientId, Date checkInDate, Date checkOutDate, Status status) {
        this.roomId = roomId;
        this.clientId = clientId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
    }

    public OccupancyOfRoom() {
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OccupancyOfRoom{" +
                "roomId=" + roomId +
                ", clientId=" + clientId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", status=" + status +
                '}';
    }
}
