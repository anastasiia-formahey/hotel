package com.anastasiia.dao;


import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Database Manager. Works with MySQL Database
 * @author Anastasiia Formahei
 * */

public class DBManager {
    private static DataSource dataSource;
    private static final Logger log = Logger.getLogger(DBManager.class);

    private DBManager(){}

    public static synchronized DataSource getInstance(){
        if(dataSource == null){
            dataSource = new DBManager().getDataSource();
        }
        return dataSource;
    }

    /**
     * This method returns a database connection pool from the context.xml
     * @return a datasource
     * */
    public DataSource getDataSource(){

        log.debug("Method starts");
        try {
            Context envContext = new InitialContext();
            dataSource = (DataSource) envContext.lookup("java:comp/env/jdbc/hotel");
        } catch (NamingException e) {
            log.error("Cannot obtain a connection pool", e);

        }
        log.debug("Method finished");
        return dataSource;
    }
}
