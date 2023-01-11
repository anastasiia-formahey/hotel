package com.anastasiia.entity;

import java.time.LocalDate;

public class Invoice {
    private int idOfRoom;
    private int idOfUser;
    private double price;
    private LocalDate createdDate;
    private LocalDate finishedDate;
    private boolean isPaid;
}
