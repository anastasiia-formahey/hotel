package com.anastasiia.dao;

import com.anastasiia.entity.Request;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.utils.Status;

import java.util.List;

/**
 * Interface that implements behavior for RequestDAO
 */
public interface IRequestDAO {
    /**
     * Method inserts new <code>Request</code> objects
     * @param requests list of <code>Request</code> objects to insert
     */
    boolean insertRequest(List<Request> requests) throws DAOException;

    /**
     * Method update <code>Status</code> of <code>Request</code> object by <code>Application</code> identity
     * @param applicationId <code>Application</code> identity
     * @param status <code>Status</code> object to update (<tt>CONFIRMED</tt>, <tt>NOT_CONFIRMED</tt>)
     */
    void updateStatus(int applicationId, Status status) throws DAOException;

    /**
     * Method selects records from the table by <code>Application</code> identity
     * @param id <code>Application</code> identity
     * @return list of <code>Request</code> objects
     */
    List<Request> selectByApplicationId(int id) throws DAOException;
    List<Request> selectNotConfirmed() throws DAOException;
}
