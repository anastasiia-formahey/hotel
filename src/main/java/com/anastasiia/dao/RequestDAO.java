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

public class RequestDAO {
    private static final Logger log = Logger.getLogger(RequestDAO.class);
    private static DataSource dataSource;
    public RequestDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void insertRequest(List<Request> requests) throws SQLException {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_REQUEST);
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
            log.trace("Close connection with DBManager");
            throw new SQLException(ex);
        }finally {
           log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
    }
    public void updateStatus(int applicationId, Status status) {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_REQUEST_STATUS);
        ){
             log.trace("Get connection with database by DBManager");
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, applicationId);
            preparedStatement.executeUpdate();
            connection.commit();
            log.trace("Query execution => " + preparedStatement);
        } catch (SQLException e) {
            log.error("Cannot execute the query ==> " + e);
            log.trace("Close connection with DBManager");
        }finally {
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
    }
    public List<Request> selectByApplicationId(int id){
        List<Request> requests = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_REQUEST_BY_APPLICATION_ID);
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
            log.trace("Close connection with DBManager");

        }finally {
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return requests;
    }
}
