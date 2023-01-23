package com.anastasiia.dao;

import com.anastasiia.entity.Application;
import com.anastasiia.entity.EntityMapper;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.SqlQuery;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    private static final Logger log = Logger.getLogger(ApplicationDAO.class);

    private static ApplicationDAO instance = null;

    private ApplicationDAO(){}

    public static synchronized ApplicationDAO getInstance(){
        if(instance == null){
            instance = new ApplicationDAO();
        }
        return instance;
    }

    public void insertApplication(Application application){
        log.debug(application.toString());
        log.debug("Method starts");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with database by DBManager");
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_APPLICATION, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, application.getClientId());
            preparedStatement.setInt(2, application.getNumberOfGuests());
            preparedStatement.setString(3, application.getClassOfRoom().name());
            preparedStatement.setInt(4, application.getLengthOfStay());
            preparedStatement.setString(5, application.getStatus().name());


            preparedStatement.executeUpdate();
            log.trace("Query execution => " + preparedStatement);
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                application.setId(resultSet.getInt(1));
            }
            resultSet.close();

        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + e);
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
    }

    public List<Application> selectAllApplications(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        ArrayList <Application> listOfApplications = new ArrayList<>();

        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_APPLICATIONS);
            log.debug(preparedStatement.executeQuery());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
               listOfApplications.add(new ApplicationMapper().mapRow(resultSet));
            }

        }catch (SQLException e){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + e);
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
        return listOfApplications;
    }
    public List<Application> selectAllApplications(int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        ArrayList <Application> listOfApplications = new ArrayList<>();

        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_APPLICATIONS_BY_USER_ID);
            preparedStatement.setInt(1, id);
            log.debug(preparedStatement.executeQuery());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
               listOfApplications.add(new ApplicationMapper().mapRow(resultSet));
            }

        }catch (SQLException e){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + e);
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
        return listOfApplications;
    }
    public List<Application> selectAllApplications(int currentPage, int amount, String orderBy){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        ArrayList <Application> listOfApplications = new ArrayList<>();

        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_APPLICATIONS+ " ORDER BY "+ orderBy + " LIMIT "+ currentPage +"," + amount);
            log.debug(preparedStatement.executeQuery());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
               listOfApplications.add(new ApplicationMapper().mapRow(resultSet));
            }

        }catch (SQLException e){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + e);
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
        return listOfApplications;
    }
    public List<Application> selectAllApplications(int currentPage, int amount, String orderBy, int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        ArrayList <Application> listOfApplications = new ArrayList<>();

        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_APPLICATIONS_BY_USER_ID + " ORDER BY "+ orderBy + " LIMIT "+ currentPage +"," + amount);
            preparedStatement.setInt(1,id);
            log.debug(preparedStatement.executeQuery());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfApplications.add(new ApplicationMapper().mapRow(resultSet));
            }

        }catch (SQLException e){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + e);
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
        return listOfApplications;
    }

    public void updateStatus(int applicationId, Status status) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with database by DBManager");
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_APPLICATION_STATUS);
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, applicationId);
            preparedStatement.executeUpdate();
            log.trace("Query execution => " + preparedStatement);
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + e);
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
    }

    private static class ApplicationMapper implements EntityMapper<Application>{
        public Application mapRow(ResultSet resultSet){
            try {
                Application application = new Application();
                application.setId(resultSet.getInt("id"));
                application.setClientId(resultSet.getInt("client_id"));
                application.setNumberOfGuests(resultSet.getInt("number_of_guests"));
                application.setClassOfRoom(ClassOfRoom.valueOf(resultSet.getString("apartment_class")));
                application.setLengthOfStay(Integer.parseInt(resultSet.getString("length_of_stay")));
                application.setStatus(Status.valueOf(resultSet.getString("status")));
                return application;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
}
}
