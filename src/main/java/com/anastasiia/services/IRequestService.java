package com.anastasiia.services;

import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.entity.Request;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.utils.Status;

import java.util.List;

/**
 * The interface implements behavior for RequestService.
 */
public interface IRequestService {
    /**
     * @param requestDTO RequestDTO object
     * @param applicationDTO ApplicationDTO object
     */
    boolean insertRequest(RequestDTO requestDTO, ApplicationDTO applicationDTO) throws ServiceException;

    /**
     * @param id Application identity
     * @return RequestDTO object
     */
    RequestDTO getRequestByApplicationId(int id) throws ServiceException;

    /**
     * Method generates RequestDTO object from Request entity objects
     * @param requests list of Request entity objects
     * @return RequestDTO object
     */
    RequestDTO entityToDTO(List<Request> requests) throws ServiceException;

    /**
     * Method generates list of Request entity objects from RequestDTO object
     * @param requestDTO RequestDTO object
     * @return list of Request entity objects
     */
    List<Request> dtoToEntity(RequestDTO requestDTO);

    /**
     * Method updates Status to <b>CONFIRMED</b> by Application identity
     * @param applicationId Application identity
     */
    void updateStatus(int applicationId, Status status) throws ServiceException;


}
