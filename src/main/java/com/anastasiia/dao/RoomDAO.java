package com.anastasiia.dao;

import com.anastasiia.entity.Room;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.SqlQuery;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    private static final Logger log = Logger.getLogger(RoomDAO.class);

    private static RoomDAO instance = null;

    private RoomDAO(){}

    public static synchronized RoomDAO getInstance() {
        if(instance==null){
            instance = new RoomDAO();
        }
        return instance;
    }

    public boolean insertRoom(Room room){
        log.debug("Method starts");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with database by DBManager");
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_ROOM, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, room.getNumberOfPerson());
            preparedStatement.setDouble(2, room.getPrice());
            preparedStatement.setString(3, room.getClassOfRoom().name());
            preparedStatement.setString(4, room.getImage());

            preparedStatement.executeUpdate();
            log.trace("Query execution => " + preparedStatement);
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                room.setId(resultSet.getInt(1));
            }
            resultSet.close();
        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
            return false;
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return true;
    }

    public List<Room> selectAllRooms() {

        ArrayList<Room> listOfRooms = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_ROOMS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Room room = new Room();
                room.setId(resultSet.getInt("id"));
                room.setNumberOfPerson(resultSet.getInt("number_of_person"));
                room.setPrice(resultSet.getDouble("price"));
                room.setClassOfRoom(ClassOfRoom.valueOf(resultSet.getString("class_of_room")));
                room.setImage(resultSet.getString("image"));
                listOfRooms.add(room);
            }
            resultSet.close();

        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return listOfRooms;

    }

    public Room findRoomById(int id) {

        ArrayList<Room> listOfRooms = new ArrayList<>();
        Room room = new Room();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ROOM_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                room.setId(resultSet.getInt("id"));
                room.setNumberOfPerson(resultSet.getInt("number_of_person"));
                room.setPrice(resultSet.getDouble("price"));
                room.setClassOfRoom(ClassOfRoom.valueOf(resultSet.getString("class_of_room")));
                room.setImage(resultSet.getString("image"));
            }
            resultSet.close();

        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return room;

    }

    public void updateRoom(Room room){
        log.debug("Method starts");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with database by DBManager");
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_ROOM_BY_ID);
            preparedStatement.setInt(1, room.getNumberOfPerson());
            preparedStatement.setDouble(2, room.getPrice());
            preparedStatement.setString(3, room.getClassOfRoom().name());
            preparedStatement.setString(4, room.getImage());
            preparedStatement.setInt(5, room.getId());

            preparedStatement.executeUpdate();
            log.trace("Query execution => " + preparedStatement);

        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
            return;
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
    }

    public List<Room> selectAllRooms(int startPage, int amount, String orderBy) {
        ArrayList<Room> listOfRooms = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(
                    SqlQuery.SQL_SELECT_ALL_ROOMS + " ORDER BY "+ orderBy + " LIMIT "+ startPage +"," + amount);
            log.debug(preparedStatement.executeQuery());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Room room = new Room();
                room.setId(resultSet.getInt("id"));
                room.setNumberOfPerson(resultSet.getInt("number_of_person"));
                room.setPrice(resultSet.getDouble("price"));
                room.setClassOfRoom(ClassOfRoom.valueOf(resultSet.getString("class_of_room")));
                room.setImage(resultSet.getString("image"));
                listOfRooms.add(room);
            }
            resultSet.close();

        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return listOfRooms;
    }

    public List<Room> selectRoomsForBooking(int numberOfPerson,Date checkInDate,Date checkOutDate,
                                            int startPage, int amount, String orderBy) {
        ArrayList<Room> listOfRooms = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(
                    SqlQuery.SQL_SELECT_ROOMS_FOR_BOOKING+ " ORDER BY "+ orderBy + " LIMIT "+ startPage +"," + amount);
            preparedStatement.setInt(1, numberOfPerson);
            preparedStatement.setInt(2, numberOfPerson);
            preparedStatement.setDate(3, checkInDate);
            preparedStatement.setDate(4, checkOutDate);
            preparedStatement.setDate(5, checkInDate);
            preparedStatement.setDate(6, checkOutDate);
            preparedStatement.setInt(7, numberOfPerson);
            log.debug(preparedStatement.executeQuery());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Room room = new Room();
                room.setId(resultSet.getInt("id"));
                room.setNumberOfPerson(resultSet.getInt("number_of_person"));
                room.setPrice(resultSet.getDouble("price"));
                room.setClassOfRoom(ClassOfRoom.valueOf(resultSet.getString("class_of_room")));
                room.setImage(resultSet.getString("image"));
                listOfRooms.add(room);
            }
            resultSet.close();

        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return listOfRooms;
    }
    public List<Room> selectRoomsForBooking(int numberOfPerson,Date checkInDate,Date checkOutDate) {
        ArrayList<Room> listOfRooms = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(
                    SqlQuery.SQL_SELECT_ROOMS_FOR_BOOKING);
            preparedStatement.setInt(1, numberOfPerson);
            preparedStatement.setInt(2, numberOfPerson);
            preparedStatement.setDate(3, checkInDate);
            preparedStatement.setDate(4, checkOutDate);
            preparedStatement.setDate(5, checkInDate);
            preparedStatement.setDate(6, checkOutDate);
            preparedStatement.setInt(7, numberOfPerson);
            log.debug(preparedStatement.executeQuery());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Room room = new Room();
                room.setId(resultSet.getInt("id"));
                room.setNumberOfPerson(resultSet.getInt("number_of_person"));
                room.setPrice(resultSet.getDouble("price"));
                room.setClassOfRoom(ClassOfRoom.valueOf(resultSet.getString("class_of_room")));
                room.setImage(resultSet.getString("image"));
                listOfRooms.add(room);
            }
            resultSet.close();

        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return listOfRooms;
    }

}
