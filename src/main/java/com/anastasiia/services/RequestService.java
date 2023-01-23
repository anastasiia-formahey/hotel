package com.anastasiia.services;

import com.anastasiia.dao.ApplicationDAO;
import com.anastasiia.dao.OccupancyOfRoomDAO;
import com.anastasiia.dao.RequestDAO;
import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.entity.Request;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class RequestService {
    private static final Logger log = Logger.getLogger(RequestService.class);

    public void insertRequest(RequestDTO requestDTO, ApplicationDTO applicationDTO){
        log.debug(requestDTO.toString());
        RequestDAO.getInstance().insertRequest(dtoToEntity(requestDTO));
        ApplicationDAO.getInstance().updateStatus(requestDTO.getApplicationId(), Status.REVIEWED);
        for(RequestDTO.RequestElement requestElement: requestDTO.getRequestElements())
        OccupancyOfRoomDAO.getInstance().insertOccupancyOfRoom(
                requestElement.getRoom().getId(),
                applicationDTO.getUserDTO().getId(),
                requestElement.getCheckInDate(),
                requestElement.getCheckOutDate(),
                Status.NOT_CONFIRMED
        );
    }

    public RequestDTO getRequestByApplicationId(int id){
        return entityToDTO(RequestDAO.getInstance().selectByApplicationId(id));
    }

    public RequestDTO entityToDTO(List<Request> requests){
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setApplicationId(requests.get(0).getApplicationId());
        for (Request request: requests) {
            requestDTO.setRequestElements(new RoomService().findRoomById(request.getRoomId()),
                    request.getCheckInDate(),
                    request.getCheckOutDate());
        }
        requestDTO.setStatusOfRequest(requests.get(0).getStatus());
        return requestDTO;
    }

    public List<Request> dtoToEntity(RequestDTO requestDTO){
        List<Request> requests = new ArrayList<>();
        log.debug(requestDTO.toString());
        for(RequestDTO.RequestElement requestElement: requestDTO.getRequestElements()){
            requests.add(new Request(requestDTO.getApplicationId(),
                    requestElement.getCheckInDate(),
                    requestElement.getCheckOutDate(),
                    requestElement.getRoom().getId(),
                    requestDTO.getStatusOfRequest()));
        }
        requests.forEach(log::debug);
        return requests;
    }

    public void updateStatus(int applicationId) {
        RequestDAO.getInstance().updateStatus(applicationId, Status.CONFIRMED);
        ApplicationDAO.getInstance().updateStatus(applicationId, Status.CONFIRMED);
    }
}
