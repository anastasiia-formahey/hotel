package com.anastasiia.utils;

public interface JspAttributes {

    //USER
    String CLIENT_ID = "clientId";
    String FIRST_NAME_JSP = "firstName";
    String LAST_NAME_JSP = "lastName";
    String EMAIL = "email";
    String PASSWORD = "password";
    String ROLE = "role";

    //notifications
    String EMAIL_EXISTS = "emailExist";
    String ERROR = "error";

    //general
    String LOCALE = "locale";
    String ROOMS = "rooms";
    String APPLICATIONS = "applications";
    String BOOKINGS = "bookings";
    String NUMBER_OF_ROOM = "numberOfRoom";
    String ROOM = "room";
    String NUMBER_OF_PERSONS = "numberOfPerson";
    String NUMBER_OF_PERSONS_APP = "numberOfPersonApp";
    String PRICE = "price";
    String CLASS_OF_ROOM = "classOfRoom";
    //pagination
    String CURRENT_PAGE = "currentPage";
    String ORDER_BY = "orderBy";


    String IMAGE = "image";
    String MESSAGE_SUCCESS = "messageSuccess";
    String CAPACITY = "capacity";
    String ROOM_EDIT = "roomEdit";
    String CHECK_IN_DATE = "checkInDate";
    String CHECK_IN_DATE_APP = "checkInDateApp";
    String CHECK_OUT_DATE = "checkOutDate";
    String CHECK_OUT_DATE_APP = "checkOutDateApp";
    String ROOM_ID = "roomId";
    String USER = "user";
    String FEATURES = "features";
    String LENGTH_OF_STAY = "lengthOfStay";
}
