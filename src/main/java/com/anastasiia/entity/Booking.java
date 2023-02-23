package com.anastasiia.entity;

import com.anastasiia.dto.BookingDTO;
import com.anastasiia.services.RoomService;
import com.anastasiia.services.UserService;
import com.anastasiia.utils.Status;

import java.sql.Date;
/**
 * <code>Booking</code> - class entity for table 'booking'
 */
public class Booking extends Entity{
    private int id;
    private int roomId;
    private int clientId;
    private Date checkInDate;
    private Date checkOutDate;
    private Double price;
    private Date dateOfBooking;
    private Status statusOfBooking;

    private Date bookingExpirationDate;

    public Booking(){}

    public Booking(int roomId, int clientId, Date checkInDate, Date checkOutDate, Double price, Date dateOfBooking, Status statusOfBooking, Date bookingExpirationDate) {
        this.roomId = roomId;
        this.clientId = clientId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.price = price;
        this.dateOfBooking = dateOfBooking;
        this.statusOfBooking = statusOfBooking;
        this.bookingExpirationDate = bookingExpirationDate;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", clientId=" + clientId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", price=" + price +
                ", dateOfBooking=" + dateOfBooking +
                ", statusOfBooking=" + statusOfBooking +
                ", bookingExpirationDate=" + bookingExpirationDate +
                '}';
    }
}
