package com.anastasiia.dto;

import com.anastasiia.entity.Booking;
import com.anastasiia.entity.Room;
import com.anastasiia.services.RoomService;
import com.anastasiia.services.UserService;
import com.anastasiia.utils.Status;

import java.sql.Date;

public class BookingDTO {
    private int id;
    private int roomId;
    private Room room;
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
    public BookingDTO(Room room, UserDTO userDTO, Date checkInDate, Date checkOutDate, Double price, Date dateOfBooking) {
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
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
    public BookingDTO entityToDTO(Booking booking){
        //todo move method to service
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
        //todo move method to service
        Booking booking = new Booking();
        booking.setId(this.getId());
        booking.setRoomId(this.getRoom().getId());
        booking.setClientId(this.getUser().dtoToEntity().getId());
        booking.setCheckInDate(this.getCheckInDate());
        booking.setCheckOutDate(this.getCheckOutDate());
        booking.setPrice(this.getPrice());
        booking.setDateOfBooking(this.getDateOfBooking());
        booking.setStatusOfBooking(this.getStatusOfBooking());
        booking.setBookingExpirationDate();
        return booking;
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
