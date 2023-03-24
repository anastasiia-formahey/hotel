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

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
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
    public void updateStatus(int applicationId, Status status) throws ServiceException {
        try {
            requestDAO.updateStatus(applicationId, status);
            applicationDAO.updateStatus(applicationId, status);
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            throw new ServiceException(e);
        }
    }

    public void checkRequests(){
        try {
            List<Request> requestList = requestDAO.selectNotConfirmed();
            for (Request request: requestList) {
                if(withDrawnRequest(request)) updateStatus(request.getApplicationId(), Status.CANCELED);
                occupancyOfRoomDAO.updateStatus(request.getRoomId(), Status.CANCELED,request.getCheckInDate(),request.getCheckOutDate());
            }
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
        }catch (ServiceException e){
            log.error("ServiceException was caught. Cause : "+ e);
        }

    }
    private boolean withDrawnRequest(Request request){
        Date expirationDate = Date.valueOf(request.getCreatingDate().toLocalDate().plusDays(2));
        if(request.getCheckInDate().before(expirationDate)){
            expirationDate = request.getCheckInDate();
        }
        return expirationDate.before(new Date(Calendar.getInstance().getTime().getTime()));
    }

    public void cancelRequest(RequestDTO requestDTO) throws ServiceException {
        if(requestDTO.getStatusOfRequest() != Status.CONFIRMED) {
            updateStatus(requestDTO.getApplicationId(), Status.CANCELED);
            for (RequestDTO.RequestElement requestElement : requestDTO.getRequestElements()) {
                try {
                    occupancyOfRoomDAO.updateStatus(
                            requestElement.getRoom().getId(),
                            Status.CANCELED,
                            requestElement.getCheckInDate(),
                            requestElement.getCheckOutDate());
                } catch (DAOException e) {
                    throw new ServiceException(e);
                }
            }
        }else throw new ServiceException(JspAttributes.CANCEL_REQUEST_EXCEPTION);
    }

}
