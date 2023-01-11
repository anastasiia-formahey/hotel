package com.anastasiia.dao;


import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database Manager. Works with MySQL Database
 * @author Anastasiia Formahei
 * */

public class DBManager {

    private static final Logger log = Logger.getLogger(DBManager.class);

    private static DBManager instance = null;

    private DBManager(){}

    public static synchronized DBManager getInstance(){
        if(instance == null){
            instance = new DBManager();
        }
        return instance;
    }

    /**
     * This method returns a database connection from the pool connection
     * @return a database connection
     * */
    public Connection getConnection(){

        log.debug("Method starts");

        Connection connection = null;
        try {
            Context envContext = new InitialContext();
            DataSource dataSource = (DataSource) envContext.lookup("java:comp/env/jdbc/hotel");
            connection = dataSource.getConnection();

        } catch (NamingException e) {
            log.error("Cannot obtain a connection from the pool", e);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.debug("Method finished");
        return connection;
    }

    /**
     * This method commits and closes the given connection
     * @param connection Connection to be committed and closed
     * */
    public void commitAndClose(Connection connection){

        log.debug("Method starts");
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.debug("Method finished");
    }

    /**
     * This method rollbacks and closes the given connection
     * @param connection Connection to be rollback and closed
     * */
    public void rollbackAndClose(Connection connection){
        log.debug("Method starts");

        try {
            connection.rollback();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.debug("Method finished");
    }

}
