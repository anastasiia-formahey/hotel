package com.anastasiia.dao;

import com.anastasiia.entity.Feature;
import com.anastasiia.entity.Room;
import com.anastasiia.utils.SqlQuery;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeaturesDAO {
    private static final Logger log = Logger.getLogger(FeaturesDAO.class);
    private static FeaturesDAO instance = null;
    private FeaturesDAO(){}

    public static synchronized FeaturesDAO getInstance(){
        if(instance == null){
            instance = new FeaturesDAO();
        }
        return instance;
    }

    public List<Feature> selectAll(){
        List <Feature> features = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SELECT_ALL_FEATURES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Feature feature = new Feature(resultSet.getInt( "id"), resultSet.getString( "name"));
                features.add(feature);
            }
        } catch (SQLException ex){
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
        return features;
    }

    public void insertRoomFeatures(Room room, Feature features){
        log.debug("Method starts");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with database by DBManager");

            preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_ROOM_FEATURES);
            preparedStatement.setInt(1, room.getId());
            preparedStatement.setInt(2, features.getId());
            preparedStatement.executeUpdate();
            log.trace("Query execution => " + preparedStatement);

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

    }

    public List<Feature> selectAll(int id){
        log.debug("Method starts");
        List <Feature> features = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ROOM_FEATURES);
            preparedStatement.setInt(1, id);
            log.trace("Query execution => " + preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Feature feature = new Feature(resultSet.getInt( "id"), resultSet.getString( "name"));
                feature.setChecked(true);
                features.add(feature);
                log.debug(feature.toString());
            }
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
        return features;
    }

    public void updateRoomFeatures(Room room, List<Feature> features){
        log.debug("Method starts");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            log.trace("Transactions");
            preparedStatement = connection.prepareStatement(SqlQuery.SQL_DELETE_FEATURES);
            preparedStatement.setInt(1, room.getId());
            log.trace("Query execution => " + preparedStatement);
            preparedStatement.executeUpdate();
            for (Feature feature: features) {
                preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_ROOM_FEATURES);
                preparedStatement.setInt(1, room.getId());
                preparedStatement.setInt(2, feature.getId());
                preparedStatement.executeUpdate();
            }
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
    }
}
