package com.anastasiia.dao;

public interface SqlQuery {

    //user
    String SQL_INSERT_USER
            = "INSERT INTO `hotel`.`user` VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?";
    String SQL_FIND_USER_BY_ID = "SELECT * FROM user WHERE id=?";

    //room
    String SQL_INSERT_ROOM
            = "INSERT INTO `hotel`.`room` VALUES (DEFAULT, ?, ?, ?, ?)";
    String SQL_SELECT_ALL_ROOMS = "SELECT * FROM room";
    String SQL_SELECT_ALL_ROOMS_FOR_MAP
            = "select id, number_of_person, price, class_of_room, image, status from room LEFT join hotel.occupancy_of_room oor on room.id = oor.room_id where status != 'CANCELED' and date (?) between check_in_date and check_out_date";
    String SQL_SELECT_ROOMS_FOR_BOOKING = "SELECT * FROM room  where number_of_person = ? and room.id not in" +
            " (select room_id from occupancy_of_room where" +
            " DATE (?) between check_in_date and check_out_date  OR  " +
            "DATE (?) between check_in_date and check_out_date)";
    String SQL_SELECT_ROOM_BY_ID = "SELECT * FROM room WHERE id=?";
    String SQL_UPDATE_ROOM_BY_ID
            = "UPDATE room SET number_of_person=? ,price=? ,class_of_room=? ,image=? WHERE id=?";

    //application
    String SQL_INSERT_APPLICATION
            = "INSERT INTO `hotel`.`application` VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    String SQL_SELECT_ALL_APPLICATIONS = "SELECT * FROM application";
    String SQL_SELECT_ALL_APPLICATIONS_BY_USER_ID = "SELECT * FROM application WHERE client_id=?";
    String SQL_UPDATE_APPLICATION_STATUS = "UPDATE application SET status=? WHERE id=?";
    String COUNT_APPLICATION_BY_STATUS = "SELECT COUNT(id) FROM application WHERE status=?";

    //request
    String SQL_UPDATE_REQUEST_STATUS = "UPDATE request SET status=? WHERE application_id=?";
    String SQL_INSERT_REQUEST = "INSERT INTO request VALUES (?,?,?,?,DEFAULT)";
    String SQL_SELECT_REQUEST_BY_APPLICATION_ID = "SELECT * FROM request WHERE application_id=?";

    //booking
    String SQL_INSERT_BOOKING
            = "INSERT INTO `hotel`.`booking` VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ? )";
    String SQL_SELECT_ALL_BOOKINGS = "SELECT * FROM booking WHERE status!='CANCELED'";
    String SQL_SELECT_ALL_BOOKINGS_BY_USER_ID = "SELECT * FROM booking WHERE client_id=? AND status!='CANCELED'";
    String SQL_UPDATE_BOOKING_STATUS
            = "UPDATE booking SET status=? WHERE id=?";

    //features
    String SELECT_ALL_FEATURES = "SELECT * FROM features";
    //room_features
    String SQL_INSERT_ROOM_FEATURES = "INSERT INTO hotel.room_features VALUES (?, ?)";
    String SQL_SELECT_ROOM_FEATURES
            = "SELECT id, name FROM features left outer join room_features on hotel.features.id=hotel.room_features.feature_id WHERE room_id=?";
    String SQL_DELETE_FEATURES    = "DELETE FROM room_features WHERE room_id=?";


    //occupancy_of_room
    String SQL_INSERT_OCCUPANCY_OF_ROOM
            = "INSERT INTO hotel.occupancy_of_room VALUES ( ?, ?, ?, ?, ?)";
    String SQL_UPDATE_OCCUPANCY_OF_ROOM_STATUS
            = "UPDATE hotel.occupancy_of_room SET status=? WHERE room_id=?" +
            " and check_in_date=? and check_out_date=?";
    String SQL_SELECT_OCCUPANCY_OF_ROOM
            = "SELECT * FROM occupancy_of_room where room_id=? " +
            "AND client_id=? AND check_in_date=? AND check_out_date=?";
    String SQL_SELECT_OCCUPANCY_OF_ROOM_BY_ID
            = "select room_id, client_id, id , first_name, last_name, email, role, check_in_date, check_out_date, status from occupancy_of_room left join user on occupancy_of_room.client_id = user.id " +
            "    where room_id = ? and " +
            "        DATE (?) between check_in_date and check_out_date";
    String SQL_SELECT_STATUS_FROM_OCCUPANCY_OF_ROOM
            = "SELECT * FROM occupancy_of_room where room_id=? " +
            "            AND DATE(?) between check_in_date  AND check_out_date";
    String SQL_SELECT_ROOM_FROM_OCCUPANCY_OF_ROOM
            = "SELECT DISTINCT room.id, room.number_of_person, room.price, room.class_of_room, room.image " +
            "FROM room LEFT JOIN occupancy_of_room ON occupancy_of_room.room_id = room.id " +
            "            where occupancy_of_room.room_id =?" +
            " AND DATE (?) between check_in_date AND check_out_date" +
            " OR occupancy_of_room.room_id =?" +
            " AND DATE (?) between check_in_date AND check_out_date";

}
