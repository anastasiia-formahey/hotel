package com.anastasiia.dao;

import com.anastasiia.entity.Room;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.SqlQuery;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomDAO {
    private static final Logger log = Logger.getLogger(RoomDAO.class);

    private static DataSource dataSource;

    public RoomDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public int insertRoom(Room room){
        log.debug("Method starts");
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_ROOM, Statement.RETURN_GENERATED_KEYS);
        ){
             log.trace("Get connection with database by DBManager");
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
            connection.commit();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
            return 0;
        }finally {
           log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return room.getId();
    }

    public List<Room> selectAllRooms() {
        ArrayList<Room> listOfRooms = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_ROOMS);
        ){
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
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
           log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return listOfRooms;
    }
    public Map<Integer, Status> selectRoomsForMap(Date date) {
        Map<Integer, Status> idStatusMap = new HashMap<>();
        ArrayList <Integer> distinctList = new ArrayList<>();
        ResultSet resultSet;
        int roomId;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_ROOMS_FOR_MAP);
            PreparedStatement preparedStatement2 = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_ROOMS);
        ){
            preparedStatement1.setDate(1, date);
            resultSet = preparedStatement1.executeQuery();
            log.debug(date.toString());
            while (resultSet.next()){
                roomId = resultSet.getInt("id");
                idStatusMap.put(roomId, Status.valueOf(resultSet.getString("status")));
                distinctList.add(roomId);
            }
            resultSet = preparedStatement2.executeQuery();
            while (resultSet.next()){
                roomId = resultSet.getInt("id");
               if(!distinctList.contains(roomId)) {
                   idStatusMap.put(roomId, Status.FREE);
                }
            }
            resultSet.close();

        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return idStatusMap;
    }

    public Room findRoomById(int id) {
        Room room = new Room();
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ROOM_BY_ID);
        ){
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
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return room;

    }

    public void updateRoom(Room room){
        log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
        PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_ROOM_BY_ID);
        ){
            log.trace("Get connection with database by DBManager");
            preparedStatement.setInt(1, room.getNumberOfPerson());
            preparedStatement.setDouble(2, room.getPrice());
            preparedStatement.setString(3, room.getClassOfRoom().name());
            preparedStatement.setString(4, room.getImage());
            preparedStatement.setInt(5, room.getId());

            preparedStatement.executeUpdate();
            log.trace("Query execution => " + preparedStatement);
            connection.commit();

        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
           log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
    }

    public List<Room> selectAllRooms(int startPage, int amount, String orderBy) {
        ArrayList<Room> listOfRooms = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                SqlQuery.SQL_SELECT_ALL_ROOMS + " ORDER BY "+ orderBy + " LIMIT "+ startPage +"," + amount);
        ){
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
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
           log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return listOfRooms;
    }

    public List<Room> selectRoomsForBooking(int numberOfPerson,Date checkInDate,Date checkOutDate) {
        ArrayList<Room> listOfRooms = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                SqlQuery.SQL_SELECT_ROOMS_FOR_BOOKING);
        ){
            preparedStatement.setInt(1, numberOfPerson);
            preparedStatement.setDate(2, checkInDate);
            preparedStatement.setDate(3, checkOutDate);
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
           log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
           log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return listOfRooms;
    }

}
