package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.FeaturesDAO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.entity.Feature;
import com.anastasiia.entity.Room;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.utils.JspAttributes;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * FeatureService - class mediates communication between a controller and DAO/repository layer
 */
public class FeatureService {

    private static final Logger log = Logger.getLogger(FeatureService.class);
    private final FeaturesDAO featuresDAO = new FeaturesDAO(DBManager.getInstance());
    RoomService roomService = new RoomService();

    /**
     * @return list of <code>Feature</code> objects
     */
    public List<Feature> getListOfFeatures(){
        try {
            return featuresDAO.selectAll();
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            return new ArrayList<>();
        }
    }

    /**
     * Method selects all <code>Feature</code> objects form the table by specific <code>Room</code>
     * @param id <code>Room</code> identity
     * @return list of <code>Feature</code> objects for specific <code>Room</code>
     */
    public List<Feature> getListOfFeatures(int id) {
        try {
            return featuresDAO.selectAll(id);
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            return new ArrayList<>();
        }
    }

    /**
     * Method selects all <code>Feature</code> objects form the request which selected
     * @param list array of features from request
     * @return list of selected <code>Feature</code> objects
     */
    public List<Feature> getFeaturesForRoom(String[] list){

            List<Feature> featuresList = getListOfFeatures();
            List<Feature> featuresSelected = new ArrayList<>();
            try {
                for (Object selected : list) {
                for (Feature feature : featuresList) {
                    if (feature.getId() == Integer.parseInt(selected.toString())) {
                        featuresSelected.add(feature);
                    }
                }
            }
        }catch (NullPointerException e){
            log.error("NullPointerException was caught");
        }
            return featuresSelected;
    }

    /**
     * Method gets <code>Feature</code> objects form the <code>Room</code> object
     * @param room <code>Room</code> object
     * @return list of<code>Feature</code> objects by specific <code>Room</code> object
     */
    public List<Feature> getFeaturesForRoomEdit(Room room){
        List<Feature> featuresList = getListOfFeatures();
        List<Feature> featuresSelected = getListOfFeatures(room.getId());
            for (Feature featureSelected: featuresSelected) {
                for (Feature feature: featuresList) {
                    if(featureSelected.getId() == feature.getId()){
                        feature.setChecked(true);}
                    log.debug(feature.toString());
                }

            }
        return featuresList;
    }

    /**
     * Method inserts <code>Feature</code> objects to specified <code>Room</code> object
     * @param room <code>RoomDTO</code> object
     * @param features list of <code>Feature</code> objects
     */
    public void insertRoomFeatures(RoomDTO room, List<Feature> features) throws ServiceException {
        for (Feature feature: features) {
            try {
                featuresDAO.insertRoomFeatures(roomService.dtoToEntity(room), feature);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        }
    }

    /**
     * Method updates <code>Feature</code> objects by specific <code>Room</code>
     * @param room <code>Room</code> object
     */
    public void updateFeatures(Room room) throws ServiceException {
        try {
            featuresDAO.updateRoomFeatures(room, room.getFeatures());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
