package com.anastasiia.utils;

public interface JspAttributes {

    //USER
    String USER = "user";
    String FIRST_NAME_JSP = "firstName";
    String LAST_NAME_JSP = "lastName";
    String EMAIL = "email";
    String PASSWORD = "password";
    String ROLE = "role";

    //notifications
    String EMAIL_EXISTS = "emailExist";
    String ERROR = "error";
    String IS_SUCCESS = "isSuccess";
    String CONFIRM = "confirm";

    //general
    String LOCALE = "locale";
    String CURRENT_DATE = "currentDate";
    String APPLICATIONS = "applications";

    //pagination
    String CURRENT_PAGE = "currentPage";
    String ORDER_BY = "orderBy";
    String ROWS = "rows";

    //FEATURES
    String FEATURES = "features";

    //room
    String ROOMS = "rooms";
    String ROOM_ID = "roomId";
    String ROOM_MAP = "roomMap";
    String NUMBER_OF_PERSON = "numberOfPerson";
    String NUMBER_OF_ROOM = "numberOfRoom";
    String ROOM = "room";
    String PRICE = "price";
    String CLASS_OF_ROOM = "classOfRoom";
    String IMAGE = "image";
    String CAPACITY = "capacity";
    String ROOM_EDIT = "roomEdit";
    String LIST_OF_ROOM_IN_REVIEW = "listOfRoomInReview";
    String ADDED_ROOMS = "addedRooms";

    //booking
    String BOOKING = "booking";
    String BOOKINGS = "bookings";
    String BOOKING_DTOS = "bookingDTOS";
    String IS_BOOKING = "isBooking";
    String CHECK_IN_DATE = "checkInDate";
    String CHECK_IN = "checkIn";
    String CHECK_OUT_DATE = "checkOutDate";
    String CHECK_OUT = "checkOut";

    //application
    String APPLICATION = "application";
    String APPLICATION_COUNT = "applicationCount";
    String LENGTH_OF_STAY = "lengthOfStay";
    String APP = "app";
    String APPLICATION_TO_REVIEW = "applicationToReview";

    //request
    String REQUEST_DTO = "requestDTO";
    String REQUEST_SENT = "requestSent";

    //occupancyOfRoom
    String OCCUPANCY_OF_ROOM = "occupancyOfRoom";
    String DATE_OF_OCCUPANCY = "dateOfOccupancy";
    String APPLICATION_ADDED = "applicationAdded";
    String APPLICATION_ID = "applicationId";
}
