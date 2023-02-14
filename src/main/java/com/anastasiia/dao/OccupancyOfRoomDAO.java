package com.anastasiia.dao;

import com.anastasiia.entity.OccupancyOfRoom;
import com.anastasiia.entity.Room;
import com.anastasiia.utils.SqlQuery;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;

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

    public void updateStatus(int roomId, Status status){
        try(Connection connection = dataSource.getConnection();
        PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_OCCUPANCY_OF_ROOM_STATUS);
        ) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, roomId);
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
            log.debug(preparedStatement.executeQuery());
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
}
