package com.anastasiia.dao;

import com.anastasiia.entity.EntityMapper;
import com.anastasiia.entity.User;
import com.anastasiia.utils.Fields;
import com.anastasiia.utils.Role;
import com.anastasiia.utils.SqlQuery;
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
     * @throws SQLException
     */
    public void insertUser(User user) throws SQLException {
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
            throw new SQLException(ex);
        }
        log.debug("Method finished");
    }

    /**
     * Method selects a record by <code>User</code> identity from the table
     * @param id <code>User</code> identity
     * @return <code>User</code> object
     * @throws SQLException
     */
    public User findUserById(int id) throws SQLException {
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
            throw new SQLException(ex);
        }
        log.debug("Method finished");
        return user;
    }

    /**
     * Method selects a record by email from the table
     * @param email <code>User</code> email
     * @return <code>User</code> object
     */
    public User findUserByEmail(String email) {
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
        }
        log.debug("Method finished");
        return user;
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
        public User mapRow(ResultSet resultSet) {
            try{
                User user = new User();
                        user.setId(resultSet.getInt(Fields.ID));
                        user.setFirstName(resultSet.getString(Fields.FIRST_NAME));
                        user.setLastName(resultSet.getString(Fields.LAST_NAME));
                        user.setEmail(resultSet.getString(Fields.EMAIL));
                        user.setPassword(resultSet.getString(Fields.PASSWORD));
                        user.setRole(Role.valueOf(resultSet.getString(Fields.ROLE)));
                return user;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
