package com.anastasiia.dao;

import com.anastasiia.entity.EntityMapper;
import com.anastasiia.entity.User;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.utils.Role;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
/**
 * <code>UserDAO</code> - class implements data access object for <code>User</code> entity
 */
public class UserDAO {
    private static final Logger log = Logger.getLogger(UserDAO.class);
    private final DataSource dataSource;
     public UserDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    /**
     * Method inserts a new record into the table
     * @param user <code>User</code> object to insert
     */
    public void insertUser(User user) throws DAOException {
        log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
        PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_USER)
        ){
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole().name());
            preparedStatement.executeUpdate();
            connection.commit();

        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw new DAOException(ex);
        }
        log.debug("Method finished");
    }

    /**
     * Method selects a record by <code>User</code> identity from the table
     * @param id <code>User</code> identity
     * @return <code>User</code> object
     */
    public User findUserById(int id) throws DAOException {
        log.debug("Method starts");
        User user = null;
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_FIND_USER_BY_ID);
        ){
             preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                user = new UserMapper().mapRow(resultSet);
            }
            resultSet.close();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw new DAOException(ex);
        }
        log.debug("Method finished");
        return user;
    }

    /**
     * Method selects a record by email from the table
     * @param email <code>User</code> email
     * @return <code>User</code> object
     */
    public User findUserByEmail(String email) throws DAOException {
        log.debug("Method starts");
        User user = null;
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_FIND_USER_BY_EMAIL)
             ){
           preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                user = new UserMapper().mapRow(resultSet);
            }
            resultSet.close();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw new DAOException(ex);
        }
        log.debug("Method finished");
        return user;
    }

    /**
     * Method updates first name, last name and email columns in user table
     * */
    public void updateUser(String firstName, String lastName, String email, int id) throws DAOException {
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_USER)){
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            connection.commit();

        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw new DAOException(ex);
        }
    }

    public void updatePassword(String password, int id) throws DAOException {
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_UPDATE_USER_PASSWORD)){
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            connection.commit();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw new DAOException(ex);
        }
    }
    /**
     * <Code>UserMapper</Code> class that help to create <Code>User</Code> object
     * from <code>ResultSet</code>
     *
     */
    private static class UserMapper implements EntityMapper<User> {
        /**
         * Method creates object of <code>User</code> from <code>ResultSet</code>
         * @param resultSet <code>ResultSet</code> object
         * @return <code>User</code> object
         */
        public User mapRow(ResultSet resultSet) throws SQLException {

                User user = new User();
                        user.setId(resultSet.getInt(Fields.USER_ID));
                        user.setFirstName(resultSet.getString(Fields.USER_FIRST_NAME));
                        user.setLastName(resultSet.getString(Fields.USER_LAST_NAME));
                        user.setEmail(resultSet.getString(Fields.USER_EMAIL));
                        user.setPassword(resultSet.getString(Fields.USER_PASSWORD));
                        user.setRole(Role.valueOf(resultSet.getString(Fields.USER_ROLE)));
                return user;

        }
    }
}
