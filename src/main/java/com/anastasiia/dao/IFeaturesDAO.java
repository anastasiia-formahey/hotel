package com.anastasiia.dao;

import com.anastasiia.entity.Feature;
import com.anastasiia.entity.Room;
import com.anastasiia.exceptions.DAOException;

import java.util.List;

/**
 * Interface that implements behavior for FeaturesDAO
 */
public interface IFeaturesDAO {

    /**
     * Method selects all records form the table
     * @return list of <code>Feature</code> objects
     * @throws DAOException
     */
    List<Feature> selectAll() throws DAOException;

    /**
     * Method inserts new <code>Feature</code> object by specific <code>Room</code>
     * @param room <code>Room</code> object
     * @param feature <code>Feature</code> object
     * @throws DAOException
     */
    void insertRoomFeatures(Room room, Feature feature) throws DAOException;

    /**
     * Method selects all records form the table by specific <code>Room</code>
     * @param id <code>Room</code> identity
     * @return list of <code>Feature</code> objects for specific <code>Room</code>
     */
    List<Feature> selectAll(int id) throws DAOException;

    /**
     * Method updates <code>Feature</code> objects by specific <code>Room</code>
     * @param room <code>Room</code> object
     * @param features list of <code>Feature</code> objects to update
     * @throws DAOException
     */
    void updateRoomFeatures(Room room, List<Feature> features) throws DAOException;
}
