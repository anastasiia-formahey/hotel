package com.anastasiia.utils;

public enum Status {
    //Room
    FREE(1),
    BOOKED(2),
    BUSY(3),
    AVAILABLE(4),
    UNAVAILABLE(5),
    //Application
    NEW(6),
    REVIEWED(7),
    //Request
    NOT_CONFIRMED(8),
    CONFIRMED(9),
    //Booking
    PAID(10),
    CANCELED(11);

    private final int id;
    Status(int id) {
        this.id = id;
    }
    public int getStatusId(){
        return id;
    }
}
