package com.anastasiia.utils;

public enum Status {
    //Room
    FREE(1),
    BOOKED(2),
    BUSY(3),
    UNAVAILABLE(4),
    //Application
    NEW(5),
    REVIEWED(6),
    //Request
    NOT_CONFIRMED(7),
    CONFIRMED(8),
    //Booking
    PAID(9),
    CANCELED(10);

    private final int id;
    Status(int id) {
        this.id = id;
    }
    public int getStatusId(){
        return id;
    }
}
