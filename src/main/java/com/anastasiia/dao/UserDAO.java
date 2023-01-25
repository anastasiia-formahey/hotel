package com.anastasiia.dao;

import com.anastasiia.entity.EntityMapper;
import com.anastasiia.entity.User;
import com.anastasiia.utils.Fields;
import com.anastasiia.utils.Role;
import com.anastasiia.utils.SqlQuery;
import org.apache.log4j.Logger;

import java.sql.*;

public class UserDAO {
    private static final Logger log = Logger.getLogger(UserDAO.class);

    private static UserDAO instance = null;

    private UserDAO(){}

    public static synchronized UserDAO getInstance(){
        if(instance == null){
            instance = new UserDAO();
        }
        return instance;
    }


    public boolean insertUser(User user){
        log.debug("Method starts");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with database by DBManager");
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole().name());

            preparedStatement.executeUpdate();
            log.trace("Query execution => " + preparedStatement);

            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                user.setId(resultSet.getInt(1));
            }
            resultSet.close();
        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
            return false;
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
        return true;
    }
    public static User findUserById(int id) {
        log.debug("Method starts");
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_FIND_USER_BY_ID);
            preparedStatement.setInt(1, id);
            log.debug(preparedStatement.executeQuery());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                user = new UserMapper().mapRow(resultSet);
            }
            resultSet.close();

        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + ex);
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
        return user;
    }

    public static User findUserByEmail(String email) {
        //log.debug("Method starts");
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_FIND_USER_BY_EMAIL);
            preparedStatement.setString(1, email);
            //log.debug(preparedStatement.executeQuery());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                user = new UserMapper().mapRow(resultSet);
            }
            resultSet.close();

        }catch (SQLException ex){
            DBManager.getInstance().rollbackAndClose(connection);
            //log.error("Cannot execute the query ==> " + ex);
            //log.trace("Close connection with DBManager");
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
           // log.trace("Close connection with DBManager");
        }
        //log.debug("Method finished");
        return user;
    }

    private static class UserMapper implements EntityMapper<User> {
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
