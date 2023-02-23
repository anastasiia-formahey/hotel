package com.anastasiia.dao;

import com.anastasiia.entity.Application;
import com.anastasiia.entity.EntityMapper;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.SqlQuery;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *<Code>ApplicationDAO</Code> - class implements data access object for Application entity
 * */
public class ApplicationDAO {

    private static final Logger log = Logger.getLogger(ApplicationDAO.class);
    private final DataSource dataSource;

    public ApplicationDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    /**
     * Method inserts new object into table
     * @param application object to insert */
    public void insertApplication(Application application) throws SQLException {
        log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_APPLICATION)
        ) {
            preparedStatement.setInt(1, application.getClientId());
            preparedStatement.setInt(2, application.getNumberOfGuests());
            preparedStatement.setString(3, application.getClassOfRoom().name());
            preparedStatement.setInt(4, application.getLengthOfStay());
            preparedStatement.setString(5, application.getStatus().name());

            preparedStatement.executeUpdate();
            connection.commit();
            log.debug("Application has inserted");
        } catch (SQLException e) {
            log.error("Cannot execute the query ==> " + e);
            throw new SQLException(e);
        }
        log.debug("Method finished");

    }

    /**
     * Method selects all records from the table
     * @return list of applications
     * @throws SQLException
     */
    public List<Application> selectAllApplications() throws SQLException {
        log.debug("Method starts");
        ResultSet resultSet;
        ArrayList <Application> listOfApplications = new ArrayList<>();
        try(Connection  connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_APPLICATIONS)
            ){
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
               listOfApplications.add(new ApplicationMapper().mapRow(resultSet));
            }
        }catch (SQLException e){
            log.error("Cannot execute the query ==> " + e);
            throw new SQLException(e);
        }
        log.debug("Method finished");
        return listOfApplications;
    }

    /**
     * Method selects all records from the table by user identity
     * @param id user identity
     * @return list of applications by specific user
     */
    public List<Application> selectAllApplications(int id){
        log.debug("Method starts");
        ResultSet resultSet;
        ArrayList <Application> listOfApplications = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_APPLICATIONS_BY_USER_ID)
        ){
            preparedStatement.setInt(1, id);
            log.debug(preparedStatement.executeQuery());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
               listOfApplications.add(new ApplicationMapper().mapRow(resultSet));
            }
        }catch (SQLException e){
            log.error("Cannot execute the query ==> " + e);
        }
        log.debug("Method finished");
        return listOfApplications;
    }

    /**
     * Method selects all records from the table with limits.
     * This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param amount number of records to select
     * @param orderBy parameter for sorting records
     * @return list of applications with certain number of records
     */
    public List<Application> selectAllApplications(int currentPage, int amount, String orderBy){
        log.debug("Method starts");
        ResultSet resultSet;
        ArrayList <Application> listOfApplications = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_APPLICATIONS+ " ORDER BY "+ orderBy + " LIMIT "+ currentPage +"," + amount)
        ){
            log.debug(preparedStatement.executeQuery());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
               listOfApplications.add(new ApplicationMapper().mapRow(resultSet));
            }
        }catch (SQLException e){
           log.error("Cannot execute the query ==> " + e);
        }
        log.debug("Method finished");
        return listOfApplications;
    }

    /**
     * Method selects all records from the table with limits by user identity.
     * This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param amount number of records to select
     * @param orderBy parameter for sorting records
     * @param id user identity
     * @return list of applications with certain number of records by specific user
     */
    public List<Application> selectAllApplications(int currentPage, int amount, String orderBy, int id){
        log.debug("Method starts");
        ResultSet resultSet;
        ArrayList <Application> listOfApplications = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_APPLICATIONS_BY_USER_ID + " ORDER BY "+ orderBy + " LIMIT "+ currentPage +"," + amount)
        ){
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listOfApplications.add(new ApplicationMapper().mapRow(resultSet));
            }
        }catch (SQLException e){
            log.error("Cannot execute the query ==> " + e);
        }
        log.debug("Method finished");
        return listOfApplications;
    }

    /**
     * Method updates status of a record in table
     * @param applicationId application identity
     * @param status application status to update
     * @throws SQLException
     */
    public void updateStatus(int applicationId, Status status) throws SQLException {
        log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_APPLICATION_STATUS)
        ) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, applicationId);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("Cannot execute the query ==> " + e);
            throw new SQLException(e);
        }
        log.debug("Method finished");
    }

    /**
     * Method counts an amount of records by specific status
     * @param status object of class <Code>Status<Code/>
     * @return amount of records by specific <Code>status</Code>
     */
    public int applicationCountByStatus(Status status) {
        log.debug("Method starts");
        ResultSet resultSet;
        int countResult= 0;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.COUNT_APPLICATION_BY_STATUS)){
            preparedStatement.setString(1, status.name());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                countResult = resultSet.getInt("COUNT(id)");
            }
        }catch (SQLException e){
            log.error("Cannot execute the query ==> " + e);
        }
        log.debug("Method finished");
        return countResult;
    }

    /**
     * <Code>ApplicationMapper</Code> class that help to create object of <Code>Application.class</Code>
     * from <code>ResultSet</code>
     *
     */
    private static class ApplicationMapper implements EntityMapper<Application>{
        /**
         * Method creates object of <code>Application</code> from <code>ResultSet</code>
         * @param resultSet <code>ResultSet</code> object
         * @return <code>Application</code> object
         */
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
