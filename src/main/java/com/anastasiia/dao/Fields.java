package com.anastasiia.dao;

/**
 * Contains constants as name of table fields in database
 */
public interface Fields {

 //APPLICATION fields
 String APPLICATION_ID = "id";
 String APPLICATION_CLIENT_ID = "client_id";
 String APPLICATION_NUMBER_OF_GUESTS = "number_of_guests";
 String APPLICATION_APARTMENT_CLASS = "apartment_class";
 String APPLICATION_LENGTH_OF_STAY = "length_of_stay";
 String APPLICATION_STATUS = "status";
 String APPLICATION_COMMENT = "comment";

 //BOOKING fields
 String BOOKING_ID = "id";
 String BOOKING_ROOM_ID = "room_id";
 String BOOKING_CLIENT_ID = "client_id";
 String BOOKING_CHECK_IN = "check_in_date";
 String BOOKING_CHECK_OUT = "check_out_date";
 String BOOKING_PRICE = "price";
 String BOOKING_DATE_OF_BOOKING = "date_of_booking";
 String BOOKING_STATUS = "status";

 //FEATURES fields
 String FEATURES_ID = "id";
 String FEATURES_NAME = "name";

 //OCCUPANCY_OF_ROOM fields
 String OCCUPANCY_OF_ROOM_ROOM_ID = "room_id";
 String OCCUPANCY_OF_ROOM_CLIENT_ID = "client_id";
 String OCCUPANCY_OF_ROOM_CHECK_IN = "check_in_date";
 String OCCUPANCY_OF_ROOM_CHECK_OUT = "check_out_date";
 String OCCUPANCY_OF_ROOM_STATUS = "status";

 //REQUEST fields
 String REQUEST_APPLICATION_ID = "application_id";
 String REQUEST_CHECK_IN = "check_in_date";
 String REQUEST_CHECK_OUT = "check_out_date";
 String REQUEST_ROOM_ID = "room_id";
 String REQUEST_STATUS = "status";
 String REQUEST_STATUS_ID = "status_id";
 String CREATING_DATE = "creating_date";

 //ROOM fields
 String ROOM_ID = "id";
 String ROOM_NUMBER_OF_PERSON = "number_of_person";
 String ROOM_PRICE = "price";
 String ROOM_CLASS_OF_ROOM = "class_of_room";
 String ROOM_IMAGE = "image";
 String ROOM_STATUS = "status";

 //ROOM_FEATURES fields
 String ROOM_FEATURES_ROOM_ID = "room_id";
 String ROOM_FEATURES_FEATURE_ID = "feature_id";

 //USER fields
 String USER_ID = "id";
 String USER_FIRST_NAME = "first_name";
 String USER_LAST_NAME = "last_name";
 String USER_EMAIL = "email";
 String USER_PASSWORD = "password";
 String USER_ROLE = "role";

 String STATUS_ID = "status_id";
}
