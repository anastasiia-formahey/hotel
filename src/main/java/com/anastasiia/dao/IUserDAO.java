package com.anastasiia.dao;


import com.anastasiia.entity.User;
import com.anastasiia.exceptions.DAOException;

/**
 * Interface that implements behavior for UserDAO
 */
public interface IUserDAO {
    /**
     * Method inserts a new record into the table
     * @param user <code>User</code> object to insert
     */
    void insertUser(User user) throws DAOException;

    /**
     * Method selects a record by <code>User</code> identity from the table
     * @param id <code>User</code> identity
     * @return <code>User</code> object
     */
    User findUserById(int id) throws DAOException;

    /**
     * Method selects a record by email from the table
     * @param email <code>User</code> email
     * @return <code>User</code> object
     */
    User findUserByEmail(String email) throws DAOException;

    /**
     * Method updates first name, last name and email columns in user table
     * */
    void updateUser(String firstName, String lastName, String email, int id) throws DAOException;

    /**
     * Method updates user password by user identity
     * @param password new password to update
     * @param id user identity
     * */
    void updatePassword(String password, int id) throws DAOException;
}
