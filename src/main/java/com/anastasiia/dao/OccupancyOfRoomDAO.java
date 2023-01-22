package com.anastasiia.dao;

import com.anastasiia.entity.Booking;
import com.anastasiia.entity.OccupancyOfRoom;
import com.anastasiia.utils.SqlQuery;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import java.sql.*;

public class OccupancyOfRoomDAO {
    private static final Logger log = Logger.getLogger(OccupancyOfRoomDAO.class);

    private static OccupancyOfRoomDAO instance = null;

    private OccupancyOfRoomDAO(){}

    public static synchronized OccupancyOfRoomDAO getInstance() {
        if(instance ==null){
            instance = new OccupancyOfRoomDAO();
        }
        return instance;
    }

    public void insertOccupancyOfRoom(int roomId, int clientId, Date checkIn, Date checkOut, Status status){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_OCCUPANCY_OF_ROOM);
                preparedStatement.setInt(1, roomId);
                preparedStatement.setInt(2, clientId);
                preparedStatement.setDate(3, checkIn);
                preparedStatement.setDate(4, checkOut);
                preparedStatement.setString(5, status.name());
                preparedStatement.executeUpdate();

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
    }

    public void updateStatus(int roomId, Status status){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_OCCUPANCY_OF_ROOM_STATUS);
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, roomId);
            preparedStatement.executeUpdate();

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
    }

    public boolean isExist(int roomId, int clientId, Date checkIn, Date checkOut, Status status){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        OccupancyOfRoom occupancyOfRoom = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_OCCUPANCY_OF_ROOM);
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
        return occupancyOfRoom != null;
    }


}
