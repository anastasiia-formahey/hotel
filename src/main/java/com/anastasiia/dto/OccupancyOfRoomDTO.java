package com.anastasiia.dto;

import com.anastasiia.utils.Status;

import java.io.Serializable;
import java.sql.Date;
/**
 * <code>OccupancyOfRoomDTO</code> - class implements data transfer object for <code>OccupancyOfRoom entity</code>
 */
public class OccupancyOfRoomDTO implements Serializable {
    private static final long serialVersionUID = -6242272177103706745L;
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

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public UserDTO getClient() {
        return client;
    }

    public void setClient(UserDTO client) {
        this.client = client;
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

    public OccupancyOfRoomDTO updateStatus(Status status) {
        this.status = status;
        return this;
    }
}
