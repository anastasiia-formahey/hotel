package com.anastasiia.dao.impl;

import com.anastasiia.dao.Fields;
import com.anastasiia.dao.IApplicationDAO;
import com.anastasiia.dao.SqlQuery;
import com.anastasiia.entity.Application;
import com.anastasiia.entity.EntityMapper;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *<Code>ApplicationDAO</Code> - class implements data access object for Application entity
 * */
public class ApplicationDAO implements IApplicationDAO {

    private static final Logger log = Logger.getLogger(ApplicationDAO.class);
    private final DataSource dataSource;

    public ApplicationDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    /**
     * Method inserts new object into table
     * @param application object to insert */
    public boolean insertApplication(Application application) throws DAOException {
        log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_APPLICATION)
        ) {
            preparedStatement.setInt(1, application.getClientId());
            preparedStatement.setInt(2, application.getNumberOfGuests());
            preparedStatement.setString(3, application.getClassOfRoom().name());
            preparedStatement.setInt(4, application.getLengthOfStay());
            preparedStatement.setInt(5, application.getStatus().getStatusId());

            preparedStatement.executeUpdate();
            connection.commit();
            log.debug("Application has inserted");
        } catch (SQLException e) {
            log.error("Cannot execute the query ==> " + e);
            throw new DAOException(e);
        }
        log.debug("Method finished");
        return true;
    }

    /**
     * Method selects all records from the table
     * @return list of applications
     */
    public List<Application> selectAllApplications() throws DAOException {
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
            throw new DAOException(e);
        }
        log.debug("Method finished");
        return listOfApplications;
    }

    /**
     * Method selects all records from the table by user identity
     * @param id user identity
     * @return list of applications by specific user
     */
    public List<Application> selectAllApplications(int id) throws DAOException{
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
            throw new DAOException(e);
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
    public List<Application> selectAllApplications(int currentPage, int amount, String orderBy) throws DAOException{
        log.debug("Method starts");
        ResultSet resultSet;
        ArrayList <Application> listOfApplications = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ALL_APPLICATIONS+ " ORDER BY "+ orderBy + " LIMIT "+ currentPage +"," + amount)
        ){
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
               listOfApplications.add(new ApplicationMapper().mapRow(resultSet));
            }
        }catch (SQLException e){
           log.error("Cannot execute the query ==> " + e);
            throw new DAOException(e);
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
    public List<Application> selectAllApplications(int currentPage, int amount, String orderBy, int id) throws DAOException{
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
            throw new DAOException(e);
        }
        log.debug("Method finished");
        return listOfApplications;
    }

    /**
     * Method updates status of a record in table
     * @param applicationId application identity
     * @param status application status to update
     */
    public void updateStatus(int applicationId, Status status) throws DAOException {
        log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_APPLICATION_STATUS)
        ) {
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
     * Method counts common amount of records
     * @return common amount of records
     */
    public int applicationCountAll() throws DAOException{
        log.debug("Method starts");
        ResultSet resultSet;
        int countResult= 0;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.COUNT_ALL_APPLICATION)){
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                countResult = resultSet.getInt("COUNT(id)");
            }
        }catch (SQLException e){
            log.error("Cannot execute the query ==> " + e);
            throw new DAOException(e);
        }
        log.debug("Method finished");
        return countResult;
    }

    /**
     * Method counts an amount of records by specific status
     * @param status object of class <Code>Status<Code/>
     * @return amount of records by specific <Code>status</Code>
     */
    public int applicationCountByStatus(Status status) throws DAOException{
        log.debug("Method starts");
        ResultSet resultSet;
        int countResult= 0;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.COUNT_APPLICATION_BY_STATUS)){
            preparedStatement.setInt(1, status.getStatusId());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                countResult = resultSet.getInt("COUNT(id)");
            }
        }catch (SQLException e){
            log.error("Cannot execute the query ==> " + e);
            throw new DAOException(e);
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
        public Application mapRow(ResultSet resultSet) throws SQLException {
                Application application = new Application();
                application.setId(resultSet.getInt(Fields.APPLICATION_ID));
                application.setClientId(resultSet.getInt(Fields.APPLICATION_CLIENT_ID));
                application.setNumberOfGuests(resultSet.getInt(Fields.APPLICATION_NUMBER_OF_GUESTS));
                application.setClassOfRoom(ClassOfRoom.valueOf(resultSet.getString(Fields.APPLICATION_APARTMENT_CLASS)));
                application.setLengthOfStay(Integer.parseInt(resultSet.getString(Fields.APPLICATION_LENGTH_OF_STAY)));
                application.setStatus(Status.valueOf(resultSet.getString(Fields.APPLICATION_STATUS)));
                return application;
        }
}
}
