package com.anastasiia.entity;

import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;

import java.time.LocalDate;
import java.sql.Date;

/**
 * <code>Application</code> - class entity for table 'application'
 */
public class Application extends Entity {
    private int id;
    private int clientId;
    private int numberOfGuests;
    private ClassOfRoom classOfRoom;
    private int lengthOfStay;
    private Status status;

    public Application(){}

    public Application(int id, int clientId, int numberOfGuests, ClassOfRoom classOfRoom, int lengthOfStay) {
        this.id = id;
        this.clientId = clientId;
        this.numberOfGuests = numberOfGuests;
        this.classOfRoom = classOfRoom;
        this.lengthOfStay = lengthOfStay;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public ClassOfRoom getClassOfRoom() {
        return classOfRoom;
    }

    public void setClassOfRoom(ClassOfRoom classOfRoom) {
        this.classOfRoom = classOfRoom;
    }

    public int getLengthOfStay() {
        return lengthOfStay;
    }

    public void setLengthOfStay(int lengthOfStay) {
        this.lengthOfStay = lengthOfStay;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", numberOfGuests=" + numberOfGuests +
                ", classOfRoom=" + classOfRoom +
                ", lengthOfStay=" + lengthOfStay +
                '}';
    }
}
