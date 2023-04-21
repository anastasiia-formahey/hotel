package com.anastasiia.dto;

import com.anastasiia.entity.Feature;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;

import java.io.Serializable;
import java.util.List;

/**
 * <code>RoomDTO</code> - class implements data transfer object for <code>Room entity</code>
 */
public class RoomDTO implements Serializable {
    private static final long serialVersionUID = -4232369129969329322L;
    private int id;
    private int numberOfPerson;
    private double price;
    private ClassOfRoom classOfRoom;
    private String image;
    private List<Feature> features;
    private Status status;

    public RoomDTO(){}
    public RoomDTO(RoomDTOBuilder roomDTOBuilder){
        this.numberOfPerson = roomDTOBuilder.numberOfPerson;
        this.price = roomDTOBuilder.price;
        this.classOfRoom = roomDTOBuilder.classOfRoom;
        this.image = roomDTOBuilder.image;
        this.features = roomDTOBuilder.features;
        this.status = roomDTOBuilder.status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfPerson() {
        return numberOfPerson;
    }

    public void setNumberOfPerson(int numberOfPerson) {
        this.numberOfPerson = numberOfPerson;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ClassOfRoom getClassOfRoom() {
        return classOfRoom;
    }

    public void setClassOfRoom(ClassOfRoom classOfRoom) {
        this.classOfRoom = classOfRoom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static class RoomDTOBuilder{
         int numberOfPerson;
         double price;
         ClassOfRoom classOfRoom;
         String image;
         List<Feature> features;
         Status status;
         public RoomDTOBuilder(){}

        public RoomDTOBuilder withNumberOfPerson(int numberOfPerson){
             this.numberOfPerson = numberOfPerson;
             return this;
        }
        public RoomDTOBuilder withPrice(double price){
            this.price = price;
            return this;
        }
        public RoomDTOBuilder withClassOfRoom(ClassOfRoom classOfRoom){
            this.classOfRoom = classOfRoom;
            return this;
        }
        public RoomDTOBuilder withImage(String image){
            this.image = image;
            return this;
        }
        public RoomDTOBuilder withFeatures(List<Feature> features){
            this.features = features;
            return this;
        }
        public RoomDTOBuilder withStatus(Status status){
            this.status = status;
            return this;
        }
        public RoomDTO build(){
             return new RoomDTO(this);
        }
    }

}
