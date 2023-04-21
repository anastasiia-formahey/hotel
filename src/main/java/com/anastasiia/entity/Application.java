package com.anastasiia.entity;

import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;

import java.io.Serializable;

/**
 * <code>Application</code> - class entity for table 'application'
 */
public class Application implements Serializable {

    private static final long serialVersionUID = 5398286134931121061L;
    private int id;
    private int clientId;
    private int numberOfGuests;
    private ClassOfRoom classOfRoom;
    private int lengthOfStay;
    private Status status;
    private String comment;

    public Application(){}

    public Application(int id, int clientId, int numberOfGuests, ClassOfRoom classOfRoom, int lengthOfStay, String comment) {
        this.id = id;
        this.clientId = clientId;
        this.numberOfGuests = numberOfGuests;
        this.classOfRoom = classOfRoom;
        this.lengthOfStay = lengthOfStay;
        this.comment = comment;
    }


    public int getId() {
        return id;
    }

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
