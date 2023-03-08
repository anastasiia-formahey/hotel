package com.anastasiia.dao;

import com.anastasiia.entity.Feature;
import com.anastasiia.entity.Room;
import com.anastasiia.exceptions.DAOException;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <Code>FeaturesDAO</Code> - class implements data access object for <code>Feature</code> entity
 */
public class FeaturesDAO {
    private static final Logger log = Logger.getLogger(FeaturesDAO.class);
    private final DataSource dataSource;
    public FeaturesDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    /**
     * Method selects all records form the table
     * @return list of <code>Feature</code> objects
     * @throws SQLException
     */
    public List<Feature> selectAll() throws DAOException {
        log.debug("Method starts");
        List <Feature> features = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SELECT_ALL_FEATURES)){
           resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Feature feature = new Feature(resultSet.getInt( Fields.FEATURES_ID), resultSet.getString( Fields.FEATURES_NAME));
                features.add(feature);
            }
        } catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw new DAOException(ex);
        }
        log.debug("Method finished");
        return features;
    }

    /**
     * Method inserts new <code>Feature</code> object by specific <code>Room</code>
     * @param room <code>Room</code> object
     * @param feature <code>Feature</code> object
     * @throws SQLException
     */
    public void insertRoomFeatures(Room room, Feature feature) throws DAOException {
        log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_ROOM_FEATURES)
        ) {
            preparedStatement.setInt(1, room.getId());
            preparedStatement.setInt(2, feature.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        }catch (SQLException ex){
                log.error("Cannot execute the query ==> " + ex);
                throw new DAOException(ex);
            }
            log.debug("Method finished");

    }

    /**
     * Method selects all records form the table by specific <code>Room</code>
     * @param id <code>Room</code> identity
     * @return list of <code>Feature</code> objects for specific <code>Room</code>
     */
    public List<Feature> selectAll(int id) throws DAOException {
        log.debug("Method starts");
        List <Feature> features = new ArrayList<>();
        ResultSet resultSet;

        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_SELECT_ROOM_FEATURES);
        ){
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Feature feature = new Feature(resultSet.getInt( Fields.FEATURES_ID), resultSet.getString( Fields.FEATURES_NAME));
                feature.setChecked(true);
                features.add(feature);
                log.debug(feature.toString());
            }
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw new DAOException(ex);
        }
        log.debug("Method finished");
        return features;
    }

    /**
     * Method updates <code>Feature</code> objects by specific <code>Room</code>
     * @param room <code>Room</code> object
     * @param features list of <code>Feature</code> objects to update
     * @throws SQLException
     */
    public void updateRoomFeatures(Room room, List<Feature> features) throws DAOException {
        log.debug("Method starts");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement  preparedStatement1 = connection.prepareStatement(SqlQuery.SQL_DELETE_FEATURES);
            PreparedStatement  preparedStatement = connection.prepareStatement(SqlQuery.SQL_INSERT_ROOM_FEATURES)
        ) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            log.trace("Transactions");
            preparedStatement1.setInt(1, room.getId());
            preparedStatement1.executeUpdate();
            for (Feature feature: features) {
                preparedStatement.setInt(1, room.getId());
                preparedStatement.setInt(2, feature.getId());
                preparedStatement.executeUpdate();
            }
            connection.commit();
        }catch (SQLException ex){
            log.error("Cannot execute the query ==> " + ex);
            throw new DAOException(ex);
        }
        log.debug("Method finished");
    }
}
