package com.anastasiia.dto;

import com.anastasiia.utils.Status;

import java.io.Serializable;
import java.sql.Date;
/**
 * <code>BookingDTO</code> - class implements data transfer object for <code>Booking entity</code>
 */
public class BookingDTO implements Serializable {
    private int id;
    private int roomId;
    private RoomDTO room;
    private int numberOfPerson;
    private UserDTO userDTO;
    private Date checkInDate;
    private Date checkOutDate;
    private Double price;
    private Date dateOfBooking;
    private Status statusOfBooking;
    private Date bookingExpirationDate;

    public BookingDTO(){
    }
    public BookingDTO(RoomDTO room, UserDTO userDTO, Date checkInDate, Date checkOutDate, Double price, Date dateOfBooking) {
        this.room = room;
        this.userDTO = userDTO;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.price = price;
        this.dateOfBooking = dateOfBooking;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public int getNumberOfPerson() {
        return numberOfPerson;
    }

    public void setNumberOfPerson(int numberOfPerson) {
        this.numberOfPerson = numberOfPerson;
    }

    public UserDTO getUser() {
        return userDTO;
    }

    public void setUser(UserDTO userDTO) {
        this.userDTO = userDTO;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDateOfBooking() {
        return dateOfBooking;
    }

    public void setDateOfBooking(Date dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public Status getStatusOfBooking() {
        return statusOfBooking;
    }

    public void setStatusOfBooking(Status statusOfBooking) {
        this.statusOfBooking = statusOfBooking;
    }

    public Date getBookingExpirationDate() {
        if(bookingExpirationDate == null){
            setBookingExpirationDate();
        }
        return bookingExpirationDate;
    }

    public void setBookingExpirationDate() {
        //todo move method to service
        Date date = this.getDateOfBooking();
        Date expirationDate = Date.valueOf(date.toLocalDate().plusDays(2));
        if(this.checkInDate.before(expirationDate)){
            this.bookingExpirationDate = checkInDate;
        }else {
            this.bookingExpirationDate = expirationDate;
        }
    }

    public void setBookingExpirationDate(Date bookingExpirationDate) {
        this.bookingExpirationDate = bookingExpirationDate;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "room=" + room +
                ", userDTO=" + userDTO +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                '}';
    }
}
