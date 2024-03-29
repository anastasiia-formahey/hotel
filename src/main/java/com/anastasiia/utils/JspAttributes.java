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
    String EXCEPTION_MESSAGE = "exception";
    String INFO_MESSAGE = "infoMessage";
    String SUCCESS_MESSAGE = "successMessage";
    /*** Message - All fields have to be filled*/
    String FIELD_EXCEPTION = "fieldException";
    /*** Message - All fields with numbers have to be more than 0*/
    String NUMBER_EXCEPTION = "numberException";
    /*** Message - Invalid date*/
    String INVALID_DATE_EXCEPTION = "invalidDateException";
    /*** Message - The nearest date for booking should be no earlier than 1 day before check-in*/
    String INCORRECT_BOOK_DATE_EXCEPTION = "incorrectBookDateException";
    /*** Message - Email field has to be filled*/
    String EMAIL_EXCEPTION = "emailException";
    /*** Message - Invalid email. Email has to be in format user@gmail.com*/
    String INVALID_EMAIL_EXCEPTION = "invalidEmailException";
    /*** Message - Password field has to be filled*/
    String PASSWORD_EXCEPTION = "passwordException";
    /*** Message - Invalid password. Password has to contain only letter and number, length 4-20 symbols*/
    String INVALID_PASSWORD_EXCEPTION = "invalidPasswordException";
    /*** Message - Nothing was found for your request. Try changing the search parameters*/
    String NO_RESULT_MESSAGE = "noResult";
    /*** Message - Wrong email or password*/
    String WRONG_EMAIL_OR_PASSWORD = "wrongEmailOrPassword";
    /*** Message - The request has not been sent to the user, a reservation already exists for the selected dates*/
    String REQUEST_EXIST = "requestExist";
    /*** Message - The application has not been sent. Try again later*/
    String ADD_APPLICATION_EXCEPTION = "addApplicationException";
    String BOOKING_EXIST = "bookingExist";
    String ROOM_ADD_EXCEPTION = "roomAddException";
    String CHECK_IN_EXCEPTION = "checkInException";

    String ROOM_EDIT_EXCEPTION = "roomEditException";
    String CHECK_IN_SUCCESS = "checkInSuccess";

    String APPLICATION_ADDED = "applicationAdded";
    String BOOKING_ADDED = "bookingAdded";
    String ROOM_ADDED = "roomAdded";
    String ROOM_EDIT_SUCCESS = "roomEditSuccess";

    String CANCEL_REQUEST_EXCEPTION = "cannotCancelRequest";
    String CANCEL_REQUEST_SUCCESS = "cancelRequestSuccess";
    String EDIT_PROFILE_SUCCESS = "editProfileSuccess";
    String EDIT_PASSWORD_SUCCESS = "editPasswordSuccess";
    String SESSION_TIME_OUT = "sessionTimedOut";


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
    String COMMENT = "comment";

    //request
    String REQUEST_DTO = "requestDTO";
    String REQUEST_SENT = "requestSent";

    //occupancyOfRoom
    String OCCUPANCY_OF_ROOM = "occupancyOfRoom";
    String DATE_OF_OCCUPANCY = "dateOfOccupancy";
    String APPLICATION_ID = "applicationId";
    String TOTAL_PRICE = "totalPrice";
    String STATUS = "status";
}
