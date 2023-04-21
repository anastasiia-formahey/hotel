package com.anastasiia.entity;

import com.anastasiia.utils.Status;

import java.io.Serializable;
import java.sql.Date;
/**
 * <code>Request</code> - class entity for table 'request'
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 3763685410794372270L;
    private int applicationId;
    private Date checkInDate;
    private Date checkOutDate;
    private int roomId;
    private Status status;
    private Date creatingDate;

    public Request(int applicationId, Date checkInDate, Date checkOutDate, int roomId, Status status) {
        this.applicationId = applicationId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomId = roomId;
        this.status = status;
    }

    public Request() {
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
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

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(Date creatingDate) {
        this.creatingDate = creatingDate;
    }

    @Override
    public String toString() {
        return "Request{" +
                "applicationId=" + applicationId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", roomId=" + roomId +
                ", status=" + status +
                '}';
    }
}
