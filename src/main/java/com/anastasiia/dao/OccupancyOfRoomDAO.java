package com.anastasiia.dao;

import com.anastasiia.entity.OccupancyOfRoom;
import com.anastasiia.entity.Room;
import com.anastasiia.entity.User;
import com.anastasiia.utils.Role;
import com.anastasiia.utils.SqlQuery;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class OccupancyOfRoomDAO {
    private static final Logger log = Logger.getLogger(OccupancyOfRoomDAO.class);

    private static DataSource dataSource;

    public OccupancyOfRoomDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }


    public void insertOccupancyOfRoom(int roomId, int clientId, Date checkIn, Date checkOut, Status status){
       try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_OCCUPANCY_OF_ROOM);
        ) {
                preparedStatement.setInt(1, roomId);
                preparedStatement.setInt(2, clientId);
                preparedStatement.setDate(3, checkIn);
                preparedStatement.setDate(4, checkOut);
                preparedStatement.setString(5, status.name());
                preparedStatement.executeUpdate();
                connection.commit();
        }catch (SQLException ex){
             log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
           log.trace("Close connection with DBManager");
        }
    }

    public void updateStatus(int roomId, Status status, Date checkIn, Date checkOut){
        try(Connection connection = dataSource.getConnection();
        PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_OCCUPANCY_OF_ROOM_STATUS);
        ) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, roomId);
            preparedStatement.setDate(3, checkIn);
            preparedStatement.setDate(4, checkOut);
            preparedStatement.executeUpdate();
            connection.commit();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
           log.trace("Close connection with DBManager");
        }
    }

    public boolean isExist(int roomId, int clientId, Date checkIn, Date checkOut, Status status){
        ResultSet resultSet;
        OccupancyOfRoom occupancyOfRoom = null;

        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_OCCUPANCY_OF_ROOM);
        ) {
            preparedStatement.setInt(1, roomId);
            preparedStatement.setInt(2, clientId);
            preparedStatement.setDate(3, checkIn);
            preparedStatement.setDate(4, checkOut);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                occupancyOfRoom = new OccupancyOfRoom(
                        resultSet.getInt("room_id"),
                        resultSet.getInt("client_id"),
                        resultSet.getDate("check_in_date"),
                        resultSet.getDate("check_out_date"),
                        Status.valueOf(resultSet.getString("status"))
                );
            }
            resultSet.close();

        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            log.trace("Close connection with DBManager");
        }
        return occupancyOfRoom != null;
    }


    public Status getStatus(Room room, Date currentDate) {
        ResultSet resultSet;
        OccupancyOfRoom occupancyOfRoom = null;

        try (Connection connection = dataSource.getConnection();
        PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_STATUS_FROM_OCCUPANCY_OF_ROOM);
        ){
            preparedStatement.setInt(1, room.getId());
            preparedStatement.setDate(2, currentDate);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                occupancyOfRoom = new OccupancyOfRoom(
                        resultSet.getInt("room_id"),
                        resultSet.getInt("client_id"),
                        resultSet.getDate("check_in_date"),
                        resultSet.getDate("check_out_date"),
                        Status.valueOf(resultSet.getString("status"))
                );
            }
            resultSet.close();

        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            log.trace("Close connection with DBManager");
        }

        return occupancyOfRoom != null ? occupancyOfRoom.getStatus() : Status.FREE;
    }
    public Map<OccupancyOfRoom, User> selectByRoomId(int roomId, Date currentDate) {
        Map<OccupancyOfRoom, User> occupancyOfRoomUserMap = new HashMap<>();
        ResultSet resultSet;
        OccupancyOfRoom occupancyOfRoom = null;
        User user = new User();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_OCCUPANCY_OF_ROOM_BY_ID);
        ){
            preparedStatement.setInt(1, roomId);
            preparedStatement.setDate(2, currentDate);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                occupancyOfRoom = new OccupancyOfRoom(
                        resultSet.getInt("room_id"),
                        resultSet.getInt("id"),
                        resultSet.getDate("check_in_date"),
                        resultSet.getDate("check_out_date"),
                        Status.valueOf(resultSet.getString("status"))
                );
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
                occupancyOfRoomUserMap.put(occupancyOfRoom,user);
            }
            resultSet.close();

        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
        }finally {
            log.trace("Close connection with DBManager");
        }

        return occupancyOfRoomUserMap;
    }
}
