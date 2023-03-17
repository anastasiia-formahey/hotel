package com.anastasiia.services;

import com.anastasiia.dao.ApplicationDAO;
import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.OccupancyOfRoomDAO;
import com.anastasiia.dao.RequestDAO;
import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.entity.Request;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * RequestService - class mediates communication between a controller and DAO/repository layer
 */
public class RequestService {
    private static final Logger log = Logger.getLogger(RequestService.class);
    private final RequestDAO requestDAO = new RequestDAO(DBManager.getInstance());
    private final ApplicationDAO applicationDAO = new ApplicationDAO(DBManager.getInstance());
    private final OccupancyOfRoomDAO occupancyOfRoomDAO = new OccupancyOfRoomDAO(DBManager.getInstance());


    /**
     * @param requestDTO RequestDTO object
     * @param applicationDTO ApplicationDTO object
     */
    public boolean insertRequest(RequestDTO requestDTO, ApplicationDTO applicationDTO) throws ServiceException {
        boolean flag = false;
        try {
            flag = requestDAO.insertRequest(dtoToEntity(requestDTO));
            if(flag){
                applicationDAO.updateStatus(requestDTO.getApplicationId(), Status.REVIEWED);
                for(RequestDTO.RequestElement requestElement: requestDTO.getRequestElements()){
                    occupancyOfRoomDAO.insertOccupancyOfRoom(
                            requestElement.getRoom().getId(),
                            applicationDTO.getUserDTO().getId(),
                            requestElement.getCheckInDate(),
                            requestElement.getCheckOutDate(),
                            Status.NOT_CONFIRMED
                    );
                }
            } else throw new ServiceException(JspAttributes.REQUEST_EXIST);
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
        }
        return flag;
    }

    /**
     * @param id Application identity
     * @return RequestDTO object
     */
    public RequestDTO getRequestByApplicationId(int id) throws ServiceException {
        try {
            return entityToDTO(requestDAO.selectByApplicationId(id));
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            throw new ServiceException(e);
        }
    }

    /**
     * Method generates RequestDTO object from Request entity objects
     * @param requests list of Request entity objects
     * @return RequestDTO object
     */
    public RequestDTO entityToDTO(List<Request> requests) throws ServiceException {
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

    /**
     * Method generates list of Request entity objects from RequestDTO object
     * @param requestDTO RequestDTO object
     * @return list of Request entity objects
     */
    public List<Request> dtoToEntity(RequestDTO requestDTO){
        List<Request> requests = new ArrayList<>();
        for(RequestDTO.RequestElement requestElement: requestDTO.getRequestElements()){
            requests.add(new Request(requestDTO.getApplicationId(),
                    requestElement.getCheckInDate(),
                    requestElement.getCheckOutDate(),
                    requestElement.getRoom().getId(),
                    requestDTO.getStatusOfRequest()));
        }
        return requests;
    }

    /**
     * Method updates Status to <b>CONFIRMED</b> by Application identity
     * @param applicationId Application identity
     */
    public void updateStatus(int applicationId) throws ServiceException {
        try {
            requestDAO.updateStatus(applicationId, Status.CONFIRMED);
            applicationDAO.updateStatus(applicationId, Status.CONFIRMED);
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            throw new ServiceException(e);
        }
    }
}
