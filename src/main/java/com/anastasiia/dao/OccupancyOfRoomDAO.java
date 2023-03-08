package com.anastasiia.dao;

import com.anastasiia.entity.EntityMapper;
import com.anastasiia.entity.OccupancyOfRoom;
import com.anastasiia.entity.User;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.utils.Role;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>OccupancyOfRoomDAO</code> class implements data access object for <code>OccupancyOfRoom</code> entity
 */
public class OccupancyOfRoomDAO {
    private static final Logger log = Logger.getLogger(OccupancyOfRoomDAO.class);

    private final DataSource dataSource;

    public OccupancyOfRoomDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    /**
     * Method inserts new record occupancy of room into table
     * @param roomId <code>Room</code> identity
     * @param clientId <code>User</code> identity
     * @param checkIn the date checking in
     * @param checkOut the date checking out
     * @param status <code>Status</code> object
     */
    public void insertOccupancyOfRoom(int roomId, int clientId, Date checkIn, Date checkOut, Status status) throws DAOException {
       log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_OCCUPANCY_OF_ROOM)
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
            throw new DAOException(ex);
        }
        log.debug("Method finished");
    }

    /**
     * Method updates status an occupancy of room by <code>Room</code> identity and dates checking in/out
     * @param roomId <code>Room</code> identity
     * @param status <code>Status</code> object (<tt>NOT_CONFIRMED</tt>, <tt>BOOKED</tt>, <tt>PAID</tt>,
     *      <tt>BUSY</tt>, <tt>CANCELED</tt>)
     * @param checkIn the date checking in
     * @param checkOut the date checking out
     */
    public void updateStatus(int roomId, Status status, Date checkIn, Date checkOut) throws DAOException {
        log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
        PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_OCCUPANCY_OF_ROOM_STATUS)
        ) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, roomId);
            preparedStatement.setDate(3, checkIn);
            preparedStatement.setDate(4, checkOut);
            preparedStatement.executeUpdate();
            connection.commit();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw new DAOException(ex);
        }
        log.debug("Method finished");
    }

    /**
     * Method checks for the existence of a record by <code>Room</code> identity,
     * <code>User</code> identity and dates checking in/out
     *
     * @param roomId <code>Room</code> identity
     * @param clientId <code>User</code> identity
     * @param checkIn the date checking in
     * @param checkOut the date checking out
     * @return <b>true</b> - if the record exists according to the given parameters,
     * <p><b>false</b> - if the record does not exist</p>
     */
    public boolean isExist(int roomId, int clientId, Date checkIn, Date checkOut) throws DAOException {
        log.debug("Method starts");
        ResultSet resultSet;
        OccupancyOfRoom occupancyOfRoom = null;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_OCCUPANCY_OF_ROOM)
        ) {
            preparedStatement.setInt(1, roomId);
            preparedStatement.setInt(2, clientId);
            preparedStatement.setDate(3, checkIn);
            preparedStatement.setDate(4, checkOut);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                occupancyOfRoom = new OccupancyOfRoomMapper().mapRow(resultSet);
            }
            resultSet.close();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw new DAOException(ex);
        }
        log.debug("Method finished");
        return occupancyOfRoom != null;
    }

    /**
     * Method select <code>Status</code> by specific <code>Room</code>
     * from the table on the specified date
     *
     * @param roomId <code>Room</code> identity
     * @param date the date to know status
     * @return <code>Status</code> object (<tt>NOT_CONFIRMED</tt>, <tt>BOOKED</tt>, <tt>PAID</tt>,
     * <tt>BUSY</tt>, <tt>CANCELED</tt>)
     */
    public Status getStatus(int roomId, Date date) throws DAOException {
        log.debug("Method starts");
        ResultSet resultSet;
        OccupancyOfRoom occupancyOfRoom = null;
        try (Connection connection = dataSource.getConnection();
        PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_STATUS_FROM_OCCUPANCY_OF_ROOM)
        ){
            preparedStatement.setInt(1, roomId);
            preparedStatement.setDate(2, date);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                occupancyOfRoom = new OccupancyOfRoomMapper().mapRow(resultSet);
            }
            resultSet.close();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw new DAOException(ex);
        }
        log.debug("Method finished");
        return occupancyOfRoom != null ? occupancyOfRoom.getStatus() : Status.FREE;
    }

    /**
     * Method selects all records by specific <code>Room</code>
     *
     * @param roomId <code>Room</code> identity
     * @param date the date to select
     * @return HashMap <<code>OccupancyOfRoom</code>, <code>User</code>> - map
     * <code>OccupancyOfRoom</code> objects by specific <code>User</code>
     */
    public Map<OccupancyOfRoom, User> selectByRoomId(int roomId, Date date) throws DAOException {
        log.debug("Method starts");
        Map<OccupancyOfRoom, User> occupancyOfRoomUserMap = new HashMap<>();
        ResultSet resultSet;
        OccupancyOfRoom occupancyOfRoom;
        User user = new User();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_OCCUPANCY_OF_ROOM_BY_ID)
        ){
            preparedStatement.setInt(1, roomId);
            preparedStatement.setDate(2, date);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                occupancyOfRoom = new OccupancyOfRoomMapper().mapRow(resultSet);
                user.setId(resultSet.getInt(Fields.OCCUPANCY_OF_ROOM_CLIENT_ID));
                user.setFirstName(resultSet.getString(Fields.USER_FIRST_NAME));
                user.setLastName(resultSet.getString(Fields.USER_LAST_NAME));
                user.setEmail(resultSet.getString(Fields.USER_EMAIL));
                user.setRole(Role.valueOf(resultSet.getString(Fields.USER_ROLE)));
                occupancyOfRoomUserMap.put(occupancyOfRoom,user);
            }
            resultSet.close();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw new DAOException(ex);
        }
        log.debug("Method finished");
        return occupancyOfRoomUserMap;
    }

    /**
     * <Code>OccupancyOfRoomMapper</Code> class that help to create <Code>OccupancyOfRoom</Code> object
     * from <code>ResultSet</code>
     */
    private static class OccupancyOfRoomMapper implements EntityMapper<OccupancyOfRoom>{
        /**
         * Method creates object of <code>OccupancyOfRoom</code> from <code>ResultSet</code>
         * @param resultSet <code>ResultSet</code> object
         * @return <code>OccupancyOfRoom</code> object
         */
        @Override
        public OccupancyOfRoom mapRow(ResultSet resultSet) throws SQLException {
            OccupancyOfRoom occupancyOfRoom;
                occupancyOfRoom = new OccupancyOfRoom(
                        resultSet.getInt(Fields.OCCUPANCY_OF_ROOM_ROOM_ID),
                        resultSet.getInt(Fields.OCCUPANCY_OF_ROOM_CLIENT_ID),
                        resultSet.getDate(Fields.OCCUPANCY_OF_ROOM_CHECK_IN),
                        resultSet.getDate(Fields.OCCUPANCY_OF_ROOM_CHECK_OUT),
                        Status.valueOf(resultSet.getString(Fields.OCCUPANCY_OF_ROOM_STATUS))
                );
            return occupancyOfRoom;
        }
    }
}
