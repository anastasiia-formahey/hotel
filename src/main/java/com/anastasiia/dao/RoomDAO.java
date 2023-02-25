package com.anastasiia.dao;

import com.anastasiia.entity.EntityMapper;
import com.anastasiia.entity.Room;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>RoomDAO</code> - class implements data access object for <code>Room</code> entity
 */
public class RoomDAO {
    private static final Logger log = Logger.getLogger(RoomDAO.class);

    private final DataSource dataSource;

    public RoomDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    /**
     * Method that insert a new record into the table
     * @param room <code>Room</code> object to insert
     * @return <code>Room</code> identity as int number, 0 - if object was not insert
     */
    public int insertRoom(Room room){
        log.debug("Method starts");
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_ROOM, Statement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.setInt(1, room.getNumberOfPerson());
            preparedStatement.setDouble(2, room.getPrice());
            preparedStatement.setString(3, room.getClassOfRoom().name());
            preparedStatement.setString(4, room.getImage());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                room.setId(resultSet.getInt(1));
            }
            resultSet.close();
            connection.commit();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            return 0;
        }
        log.debug("Method finished");
        return room.getId();
    }

    /**
     * Method selects all records from the table
     * @return list of <code>Room</code> objects
     */
    public List<Room> selectAllRooms() {
        log.debug("Method starts");
        ArrayList<Room> listOfRooms = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_ROOMS)
        ){
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfRooms.add(new RoomMapper().mapRow(resultSet));
            }
            resultSet.close();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
        }
        log.debug("Method finished");
        return listOfRooms;
    }

    /**
     * Method selects records from the table by specified date
     * @param date <code>sql.Data</code> object
     * @return HashMap where <b>key</b> - <code>Room</code> identity,
     * <b>value</b> - <code>Status</code> of <code>Room</code> on specified date
     */
    public Map<Integer, Status> selectRoomsForMap(Date date) {
        log.debug("Method starts");
        Map<Integer, Status> idStatusMap = new HashMap<>();
        ArrayList <Integer> distinctList = new ArrayList<>();
        ResultSet resultSet;
        int roomId;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_ROOMS_FOR_MAP);
            PreparedStatement preparedStatement2 = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_ROOMS)
        ){
            preparedStatement1.setDate(1, date);
            resultSet = preparedStatement1.executeQuery();
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
        }
        log.debug("Method finished");
        return idStatusMap;
    }

    /**
     * Method selects <code>Room</code> object by identity
     * @param id <code>Room</code> identity
     * @return <code>Room</code> object
     */
    public Room findRoomById(int id) {
        log.debug("Method starts");
        Room room = null;
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ROOM_BY_ID)
        ){
           preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                room = new RoomMapper().mapRow(resultSet);
            }
            resultSet.close();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
        }
        log.debug("Method finished");
        return room;
    }

    /**
     * Method updates the record in the table
     * @param room <code>Room</code> object to update
     */
    public void updateRoom(Room room){
        log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
        PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_ROOM_BY_ID)
        ){
            preparedStatement.setInt(1, room.getNumberOfPerson());
            preparedStatement.setDouble(2, room.getPrice());
            preparedStatement.setString(3, room.getClassOfRoom().name());
            preparedStatement.setString(4, room.getImage());
            preparedStatement.setInt(5, room.getId());

            preparedStatement.executeUpdate();
            connection.commit();

        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
        }finally {
           log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
    }

    /**
     * Method selects all records from the table with limits. This method uses to implement a pagination
     * @param startPage start record to selecting
     * @param amount number of records to select
     * @param orderBy parameter for sorting records
     * @return list of <code>Room</code> objects with certain number of records
     */
    public List<Room> selectAllRooms(int startPage, int amount, String orderBy) {
        log.debug("Method starts");
        ArrayList<Room> listOfRooms = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                SqlQuery.SQL_SELECT_ALL_ROOMS + " ORDER BY "+ orderBy + " LIMIT "+ startPage +"," + amount)
        ){
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfRooms.add(new RoomMapper().mapRow(resultSet));
            }
            resultSet.close();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
        }
        log.debug("Method finished");
        return listOfRooms;
    }

    /**
     * Method selects all records from the table with specified parameters
     * @param numberOfPerson <code>Room</code> field number of person
     * @param checkInDate date to check in
     * @param checkOutDate date to check out
     * @return list of <code>Room</code> objects that can be booked on the dates indicated
     */
    public List<Room> selectRoomsForBooking(int numberOfPerson,Date checkInDate,Date checkOutDate) {
        log.debug("Method starts");
        ArrayList<Room> listOfRooms = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                SqlQuery.SQL_SELECT_ROOMS_FOR_BOOKING)
        ){
            preparedStatement.setInt(1, numberOfPerson);
            preparedStatement.setDate(2, checkInDate);
            preparedStatement.setDate(3, checkOutDate);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfRooms.add(new RoomMapper().mapRow(resultSet));
            }
            resultSet.close();

        }catch (SQLException ex){
           log.error("Cannot execute the query ==> " + ex);
        }
        log.debug("Method finished");
        return listOfRooms;
    }

    /**
     * <Code>RoomMapper</Code> class that help to create <Code>Room</Code> object
     * from <code>ResultSet</code>
     *
     */
    private static class RoomMapper implements EntityMapper<Room> {
        /**
         * Method creates object of <code>Room</code> from <code>ResultSet</code>
         * @param resultSet <code>ResultSet</code> object
         * @return <code>Room</code> object
         */
        public Room mapRow(ResultSet resultSet) {
            log.debug("Mapper starts");
            try{
                Room room = new Room();
                room.setId(resultSet.getInt(Fields.ROOM_ID));
                room.setNumberOfPerson(resultSet.getInt(Fields.ROOM_NUMBER_OF_PERSON));
                room.setPrice(resultSet.getDouble(Fields.ROOM_PRICE));
                room.setClassOfRoom(ClassOfRoom.valueOf(resultSet.getString(Fields.ROOM_CLASS_OF_ROOM)));
                room.setImage(resultSet.getString(Fields.ROOM_IMAGE));
                log.debug("Mapper finished");
                return room;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
