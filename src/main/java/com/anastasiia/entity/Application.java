package com.anastasiia.entity;

import com.anastasiia.utils.ClassOfRoom;

import java.time.LocalDate;
import java.sql.Date;

public class Application extends Entity {
    private int id;
    private int clientId;
    private int numberOfGuests;
    private ClassOfRoom classOfRoom;
    private int lengthOfStay;

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
