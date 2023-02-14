package com.anastasiia.dao;

import com.anastasiia.entity.Feature;
import com.anastasiia.entity.Room;
import com.anastasiia.utils.SqlQuery;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeaturesDAO {
    private static final Logger log = Logger.getLogger(FeaturesDAO.class);
    private DataSource dataSource;
    public FeaturesDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public List<Feature> selectAll() throws SQLException {
        List <Feature> features = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SELECT_ALL_FEATURES);){
           resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Feature feature = new Feature(resultSet.getInt( "id"), resultSet.getString( "name"));
                features.add(feature);
            }
        } catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
            throw new SQLException(ex);
        }finally {
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return features;
    }

    public void insertRoomFeatures(Room room, Feature features) throws SQLException {
        log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_ROOM_FEATURES);
        ) {
            log.trace("Get connection with database by DBManager");
            preparedStatement.setInt(1, room.getId());
            preparedStatement.setInt(2, features.getId());
            log.trace("preparedStatement " + preparedStatement);
            preparedStatement.executeUpdate();
            log.trace("Query execution => " + preparedStatement);
            connection.commit();
        }catch (SQLException ex){
                log.error("Cannot execute the query ==> " + ex);
                log.trace("Close connection with DBManager");
                throw new SQLException(ex);

            }finally {
                log.trace("Close connection with DBManager");
            }
            log.debug("Method finished");

    }

    public List<Feature> selectAll(int id){
        log.debug("Method starts");
        List <Feature> features = new ArrayList<>();
        ResultSet resultSet;

        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ROOM_FEATURES);
        ){
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
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");

        }finally {
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return features;
    }

    public void updateRoomFeatures(Room room, List<Feature> features) throws SQLException {
        log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement  preparedStatement1 = connection.prepareStatement(SqlQuery.SQL_DELETE_FEATURES);
            PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_ROOM_FEATURES);
        ) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            log.trace("Transactions");
           preparedStatement1.setInt(1, room.getId());
            log.trace("Query execution => " + preparedStatement1);
            preparedStatement1.executeUpdate();
            for (Feature feature: features) {
                preparedStatement.setInt(1, room.getId());
                preparedStatement.setInt(2, feature.getId());
                preparedStatement.executeUpdate();
            }
            connection.commit();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            log.trace("Close connection with DBManager");
            throw new SQLException(ex);

        }finally {
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
    }
}
