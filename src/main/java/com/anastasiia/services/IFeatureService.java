package com.anastasiia.services;


import com.anastasiia.dto.RoomDTO;
import com.anastasiia.entity.Feature;
import com.anastasiia.entity.Room;
import com.anastasiia.exceptions.ServiceException;

import java.util.List;

/**
 * The interface implements behavior for FeatureService.
 */
public interface IFeatureService {
    /**
     * @return list of <code>Feature</code> objects
     */
    List<Feature> getListOfFeatures();

    /**
     * Method selects all <code>Feature</code> objects form the table by specific <code>Room</code>
     * @param id <code>Room</code> identity
     * @return list of <code>Feature</code> objects for specific <code>Room</code>
     */
    List<Feature> getListOfFeatures(int id);

    /**
     * Method selects all <code>Feature</code> objects form the request which selected
     * @param list array of features from request
     * @return list of selected <code>Feature</code> objects
     */
    List<Feature> getFeaturesForRoom(String[] list);

    /**
     * Method gets <code>Feature</code> objects form the <code>Room</code> object
     * @param room <code>Room</code> object
     * @return list of<code>Feature</code> objects by specific <code>Room</code> object
     */
    List<Feature> getFeaturesForRoomEdit(Room room);

    /**
     * Method inserts <code>Feature</code> objects to specified <code>Room</code> object
     * @param room <code>RoomDTO</code> object
     * @param features list of <code>Feature</code> objects
     */
    void insertRoomFeatures(RoomDTO room, List<Feature> features) throws ServiceException;

    /**
     * Method updates <code>Feature</code> objects by specific <code>Room</code>
     * @param room <code>Room</code> object
     */
    void updateFeatures(Room room) throws ServiceException;
}
