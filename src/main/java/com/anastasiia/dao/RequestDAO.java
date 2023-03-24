package com.anastasiia.dao;

import com.anastasiia.entity.Request;
import com.anastasiia.entity.Room;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * <Code>RequestDAO</Code> - class implements data access object for <code>Request</code> entity
 */
public class RequestDAO {
    private static final Logger log = Logger.getLogger(RequestDAO.class);
    private final DataSource dataSource;
    public RequestDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    /**
     * Method inserts new <code>Request</code> objects
     * @param requests list of <code>Request</code> objects to insert
     */
    public boolean insertRequest(List<Request> requests) throws DAOException {
        log.debug("Method starts");
        boolean isSuccess = false;
        ResultSet resultSet;
        Room room = null;
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatementCheckExist = connection.prepareStatement(SqlQuery.SQL_SELECT_ROOM_FROM_OCCUPANCY_OF_ROOM);
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_REQUEST);
        ){
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            log.trace("Transactions");
            for (Request request: requests) {
                preparedStatementCheckExist.setInt(1, request.getRoomId());
                preparedStatementCheckExist.setDate(2, request.getCheckInDate());
                preparedStatementCheckExist.setInt(3, request.getRoomId());
                preparedStatementCheckExist.setDate(4, request.getCheckOutDate());
                resultSet = preparedStatementCheckExist.executeQuery();
                if (resultSet.next()){
                    room = new Room();
                    room.setId(resultSet.getInt("id"));
                    room.setNumberOfPerson(resultSet.getInt("number_of_person"));
                    room.setPrice(resultSet.getDouble("price"));
                    room.setClassOfRoom(ClassOfRoom.valueOf(resultSet.getString("class_of_room")));
                    room.setImage(resultSet.getString("image"));
                }
                if (room == null) {
                    preparedStatement.setInt(1, request.getApplicationId());
                    preparedStatement.setDate(2, request.getCheckInDate());
                    preparedStatement.setDate(3, request.getCheckOutDate());
                    preparedStatement.setInt(4, request.getRoomId());
                    preparedStatement.setDate(5, new Date(Calendar.getInstance().getTime().getTime()));
                    preparedStatement.executeUpdate();
                    isSuccess = true;
                }else {
                    log.trace("Booking already exists");
                    isSuccess = false;
                }
            }
            connection.commit();
        } catch (SQLException ex){
           log.error("Cannot execute the query ==> " + ex);
            throw new DAOException(ex);
        }
        log.debug("Method finished");
        return isSuccess;
    }

    /**
     * Method update <code>Status</code> of <code>Request</code> object by <code>Application</code> identity
     * @param applicationId <code>Application</code> identity
     * @param status <code>Status</code> object to update (<tt>CONFIRMED</tt>, <tt>NOT_CONFIRMED</tt>)
     */
    public void updateStatus(int applicationId, Status status) throws DAOException {
        log.debug("Method starts");
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_REQUEST_STATUS)
        ){
            preparedStatement.setInt(1, status.getStatusId());
            preparedStatement.setInt(2, applicationId);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("Cannot execute the query ==> " + e);
            throw new DAOException(e);
        }
        log.debug("Method finished");
    }

    /**
     * Method selects records from the table by <code>Application</code> identity
     * @param id <code>Application</code> identity
     * @return list of <code>Request</code> objects
     */
    public List<Request> selectByApplicationId(int id) throws DAOException {
        log.debug("Method starts");
        List<Request> requests = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_REQUEST_BY_APPLICATION_ID)
        ){
           preparedStatement.setInt(1, id);
           resultSet = preparedStatement.executeQuery();
           while (resultSet.next()){
               Request request = new Request(
                       resultSet.getInt(Fields.REQUEST_APPLICATION_ID),
                       resultSet.getDate(Fields.REQUEST_CHECK_IN),
                       resultSet.getDate(Fields.REQUEST_CHECK_OUT),
                       resultSet.getInt(Fields.REQUEST_ROOM_ID),
                       Status.valueOf(resultSet.getString(Fields.REQUEST_STATUS)));
               requests.add(request);
           }
        } catch (SQLException ex){
             log.error("Cannot execute the query ==> " + ex);
            throw new DAOException(ex);
        }
        log.debug("Method finished");
        return requests;
    }

    public List<Request> selectNotConfirmed() throws DAOException {
        List <Request> requestList = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_REQUEST_NOT_CONFIRMED)) {
        resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Request request = new Request(
                        resultSet.getInt(Fields.REQUEST_APPLICATION_ID),
                        resultSet.getDate(Fields.REQUEST_CHECK_IN),
                        resultSet.getDate(Fields.REQUEST_CHECK_OUT),
                        resultSet.getInt(Fields.REQUEST_ROOM_ID),
                        Status.valueOf(resultSet.getString(Fields.REQUEST_STATUS)));
                request.setCreatingDate(resultSet.getDate(Fields.CREATING_DATE));
                requestList.add(request);
            }
        } catch (SQLException e) {
            log.error("Cannot execute the query ==> " + e);
            throw new DAOException(e);
        }
        return requestList;
    }
}
