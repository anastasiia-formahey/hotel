package com.anastasiia.entity;

import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;

import java.util.List;
/**
 * <code>Room</code> - class entity for table 'room'
 */
public class Room extends Entity{

    private static final long serialVersionUID = -1572642506920181578L;
    private int id;
    private int numberOfPerson;
    private double price;
    private ClassOfRoom classOfRoom;
    private String image;
    private List<Feature> features;
    private Status status;

    public Room(){}

    public Room(int numberOfPerson, double price, ClassOfRoom classOfRoom, String image, Status status) {
        this.numberOfPerson = numberOfPerson;
        this.price = price;
        this.classOfRoom = classOfRoom;
        this.image = image;
        this.status = status;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
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

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", numberOfPerson=" + numberOfPerson +
                ", price=" + price +
                ", classOfRoom=" + classOfRoom +
                ", image='" + image + '\'' +
                ", features=" + features +
                ", status=" + status +
                '}';
    }
}
