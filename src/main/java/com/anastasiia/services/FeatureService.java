package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.FeaturesDAO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.entity.Feature;
import com.anastasiia.entity.Room;
import com.anastasiia.utils.JspAttributes;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeatureService {

    private static final Logger log = Logger.getLogger(FeatureService.class);
    private final FeaturesDAO featuresDAO = new FeaturesDAO(DBManager.getInstance());
    RoomService roomService = new RoomService();

    public List<Feature> getListOfFeatures(){
        try {
            return featuresDAO.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Feature> getListOfFeatures(Room room){
        return featuresDAO.selectAll(room.getId());
    }

    public List<Feature> getFeaturesForRoom(HttpServletRequest request){
        List<Feature> featuresList = getListOfFeatures();
        List<Feature> featuresSelected = new ArrayList<>();
        String[] list = request.getParameterValues(JspAttributes.FEATURES);
        for (Object selected: list) {
            for (Feature feature: featuresList) {
                if(feature.getId() == Integer.parseInt(selected.toString())){
                    featuresSelected.add(feature);
                }
            }

        }
        return featuresSelected;
    }

    public List<Feature> getFeaturesForRoomEdit(Room room){
        List<Feature> featuresList = getListOfFeatures();
        List<Feature> featuresSelected = getListOfFeatures(room);
        log.debug(featuresSelected.toString());
            for (Feature featureSelected: featuresSelected) {
                for (Feature feature: featuresList) {
                    if(featureSelected.getId() == feature.getId()){
                        feature.setChecked(true);}
                    log.debug(feature.toString());
                }

            }
        return featuresList;
    }

    public void insertRoomFeatures(RoomDTO room, List<Feature> features){
        for (Feature feature: features) {
            try {
                featuresDAO.insertRoomFeatures(roomService.dtoToEntity(room), feature);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateFeatures(Room room){
        try {
            featuresDAO.updateRoomFeatures(room, room.getFeatures());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
