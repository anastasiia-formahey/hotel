package com.anastasiia.dao;

import com.anastasiia.entity.Application;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.utils.Status;

import java.util.List;


/**
 * Interface that implements behavior for ApplicationDAO
 */
public interface IApplicationDAO {
    /**
     * Method inserts new object into table
     * @param application object to insert */
    boolean insertApplication(Application application) throws DAOException;

    /**
     * Method selects all records from the table
     * @return list of applications
     */
    List<Application> selectAllApplications() throws DAOException;

    /**
     * Method selects all records from the table by user identity
     * @param id user identity
     * @return list of applications by specific user
     */
    List<Application> selectAllApplications(int id) throws DAOException;

    /**
     * Method selects all records from the table with limits.
     * This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param amount number of records to select
     * @param orderBy parameter for sorting records
     * @return list of applications with certain number of records
     */
    List<Application> selectAllApplications(int currentPage, int amount, String orderBy) throws DAOException;

    /**
     * Method selects all records from the table with limits by user identity.
     * This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param amount number of records to select
     * @param orderBy parameter for sorting records
     * @param id user identity
     * @return list of applications with certain number of records by specific user
     */
    List<Application> selectAllApplications(int currentPage, int amount, String orderBy, int id) throws DAOException;
    /**
     * Method updates status of a record in table
     * @param applicationId application identity
     * @param status application status to update
     */
    void updateStatus(int applicationId, Status status) throws DAOException;

    /**
     * Method counts common amount of records
     * @return common amount of records
     */
    int applicationCountAll() throws DAOException;

    /**
     * Method counts an amount of records by specific status
     * @param status object of class <Code>Status<Code/>
     * @return amount of records by specific <Code>status</Code>
     */
    int applicationCountByStatus(Status status) throws DAOException;

}
