package com.anastasiia.dto;

import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;

import java.io.Serializable;

/**
 * <code>ApplicationDTO</code> - class implements data transfer object for <code>Application entity</code>
 */
public class ApplicationDTO implements Serializable {
    private static final long serialVersionUID = 5604539450348225858L;
    private int id;
    private UserDTO userDTO;
    private int numberOfGuests;
    private ClassOfRoom classOfRoom;
    private int lengthOfStay;
    private Status status;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
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
        return "ApplicationDTO{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", numberOfGuests=" + numberOfGuests +
                ", classOfRoom=" + classOfRoom +
                ", lengthOfStay=" + lengthOfStay +
                ", status=" + status +
                '}';
    }
}
