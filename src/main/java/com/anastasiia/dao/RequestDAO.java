package com.anastasiia.dao;

import com.anastasiia.entity.Request;
import com.anastasiia.utils.SqlQuery;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
     * @throws SQLException
     */
    public void insertRequest(List<Request> requests) throws SQLException {
        log.debug("Method starts");
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_REQUEST)
        ){
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            log.trace("Transactions");
            for (Request request: requests) {
                preparedStatement.setInt(1,request.getApplicationId());
                preparedStatement.setDate(2, request.getCheckInDate());
                preparedStatement.setDate(3, request.getCheckOutDate());
                preparedStatement.setInt(4, request.getRoomId());
                preparedStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex){
           log.error("Cannot execute the query ==> " + ex);
            throw new SQLException(ex);
        }
        log.debug("Method finished");
    }

    /**
     * Method update <code>Status</code> of <code>Request</code> object by <code>Application</code> identity
     * @param applicationId <code>Application</code> identity
     * @param status <code>Status</code> object to update (<tt>CONFIRMED</tt>, <tt>NOT_CONFIRMED</tt>)
     */
    public void updateStatus(int applicationId, Status status) {
        log.debug("Method starts");
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_REQUEST_STATUS)
        ){
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, applicationId);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("Cannot execute the query ==> " + e);
        }
        log.debug("Method finished");
    }

    /**
     * Method selects records from the table by <code>Application</code> identity
     * @param id <code>Application</code> identity
     * @return list of <code>Request</code> objects
     */
    public List<Request> selectByApplicationId(int id){
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
                       resultSet.getInt("application_id"),
                       resultSet.getDate("check_in_date"),
                       resultSet.getDate("check_out_date"),
                       resultSet.getInt("room_id"),
                       Status.valueOf(resultSet.getString("status"))
               );
               requests.add(request);
           }
        } catch (SQLException ex){
             log.error("Cannot execute the query ==> " + ex);
        }
        log.debug("Method finished");
        return requests;
    }
}
