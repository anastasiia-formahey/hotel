package com.anastasiia.dto;

import com.anastasiia.entity.Booking;
import com.anastasiia.services.RoomService;
import com.anastasiia.services.UserService;
import com.anastasiia.utils.Status;

import java.sql.Date;

public class BookingDTO {
    private int id;
    private int roomId;
    private int numberOfPerson;
    private UserDTO userDTO;
    private Date checkInDate;
    private Date checkOutDate;
    private Double price;
    private Date dateOfBooking;
    private Status statusOfBooking;
    private Date bookingExpirationDate;

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
        return bookingExpirationDate;
    }

    public void setBookingExpirationDate(Date bookingExpirationDate) {
        this.bookingExpirationDate = bookingExpirationDate;
    }
    public BookingDTO entityToDTO(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setRoomId(booking.getRoomId());
        bookingDTO.setNumberOfPerson(new RoomService()
                .findRoomById(booking.getRoomId())
                .getNumberOfPerson());
        bookingDTO.setUser(new UserService().getUser(booking.getClientId()));
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setPrice(booking.getPrice());
        bookingDTO.setDateOfBooking(booking.getDateOfBooking());
        bookingDTO.setStatusOfBooking(booking.getStatusOfBooking());
        bookingDTO.setBookingExpirationDate(booking.getBookingExpirationDate());
        return bookingDTO;
    }
    public Booking dtoToEntity(){
        Booking booking = new Booking();
        booking.setId(this.getId());
        booking.setRoomId(this.getRoomId());
        booking.setClientId(this.getUser().dtoToEntity().getId());
        booking.setCheckInDate(this.getCheckInDate());
        booking.setCheckOutDate(this.getCheckOutDate());
        booking.setPrice(this.getPrice());
        booking.setDateOfBooking(this.getDateOfBooking());
        booking.setStatusOfBooking(this.getStatusOfBooking());
        booking.setBookingExpirationDate();
        return booking;
    }
}
