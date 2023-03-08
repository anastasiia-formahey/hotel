package com.anastasiia.services;

import com.anastasiia.dao.ApplicationDAO;
import com.anastasiia.dao.DBManager;
import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.entity.Application;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Status;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ApplicationService - class mediates communication between a controller and DAO/repository layer
 */
public class ApplicationService {
    private static final Logger log = Logger.getLogger(ApplicationService.class);
    private final ApplicationDAO applicationDAO = new ApplicationDAO(DBManager.getInstance());


    /**
     * Method generates Application entity object from ApplicationDTO object
     * @param applicationDTO ApplicationDTO object
     * @return Application entity object
     */
    public Application dtoToEntity(ApplicationDTO applicationDTO){
        Application application = new Application();
        application.setId(applicationDTO.getId());
        application.setClientId(applicationDTO.getUserDTO().getId());
        application.setNumberOfGuests(applicationDTO.getNumberOfGuests());
        application.setClassOfRoom(applicationDTO.getClassOfRoom());
        application.setLengthOfStay(applicationDTO.getLengthOfStay());
        application.setStatus(applicationDTO.getStatus());
        return application;
    }

    /**
     * Method generates ApplicationDTO object from Application entity object
     * @param application Application entity object
     * @return ApplicationDTO object
     */
    public ApplicationDTO entityToDTO(Application application){
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setId(application.getId());
        try {
            applicationDTO.setUserDTO(new UserService().getUser(application.getClientId()));
        } catch (ServiceException e) {
            log.error("ServiceException was caught. Cause : "+ e);
        }
        applicationDTO.setClassOfRoom(application.getClassOfRoom());
        applicationDTO.setNumberOfGuests(application.getNumberOfGuests());
        applicationDTO.setLengthOfStay(application.getLengthOfStay());
        applicationDTO.setStatus(application.getStatus());
        return applicationDTO;
    }

    /**
     * Method generate Application entity and inserts this object
     * @param applicationDTO ApplicationDTO object
     */
    public void insertApplication(ApplicationDTO applicationDTO) throws ServiceException {
        try {
            applicationDAO.insertApplication(dtoToEntity(applicationDTO));
        } catch (DAOException e) {
            throw new ServiceException(JspAttributes.ADD_APPLICATION_EXCEPTION);
        }

    }

    /**
     * Method selects all Application objects and generates list of ApplicationDTO objects
     * @return list of ApplicationDTO objects
     */
    public int applicationCountAll(){
        try {
            return applicationDAO.applicationCountAll();
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            return 0;
        }
    }

    /**
     * Method selects all Application objects by user identity
     * @param id user identity
     * @return list of ApplicationDTO objects by specific user
     */
    public List<ApplicationDTO> selectAll(int id){
        try {
            return applicationDAO.selectAllApplications(id)
                    .stream().map(this::entityToDTO).collect(Collectors.toList());
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            return new ArrayList<>();
        }
    }

    /**
     * Method selects Application objects from the table with limits.
     * This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param recordsPerPage number of records to select
     * @param orderBy parameter for sorting records
     * @return list of ApplicationDTO objects with certain number of records
     */
    public List<ApplicationDTO> selectAll(int currentPage, int recordsPerPage, String orderBy){
        currentPage = currentPage * Pagination.RECORDS_PER_PAGE - recordsPerPage;
        try {
            return applicationDAO.selectAllApplications(currentPage,recordsPerPage,orderBy)
                    .stream().map(this::entityToDTO).collect(Collectors.toList());
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            return new ArrayList<>();
        }
    }

    /**
     * Method selects Application objects from the table with limits by user identity.
     * This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param recordsPerPage number of records to select
     * @param orderBy parameter for sorting records
     * @param id user identity
     * @return list of ApplicationDTO objects with certain number of records by specific user
     */
    public List<ApplicationDTO> selectAll(int currentPage, int recordsPerPage, String orderBy, int id){
        currentPage = currentPage * Pagination.RECORDS_PER_PAGE - recordsPerPage;
        try {
            return applicationDAO.selectAllApplications(currentPage,recordsPerPage,orderBy,id)
                    .stream().map(this::entityToDTO).collect(Collectors.toList());
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            return new ArrayList<>();
        }
    }

    /**
     * Method selects certain ApplicationDTO object from the list by application identity
     * @param applicationDTOList list of ApplicationDTO objects
     * @param id application identity to select
     * @return ApplicationDTO object, returns empty ApplicationDTO object if list does not contain object with specified id
     */
    public ApplicationDTO get(List<ApplicationDTO> applicationDTOList, int id){
        return applicationDTOList.stream()
                .filter(applicationDTO -> id==(applicationDTO.getId()))
                .findAny().orElse(new ApplicationDTO());
    }

    /**
     *
     * @param status object of class <Code>Status<Code/> (NEW, REVIEWED, CONFIRMED)
     * @return amount of records by specific <Code>status</Code>
     */
    public int applicationCountByStatus(Status status){
        try {
            return applicationDAO.applicationCountByStatus(status);
        } catch (DAOException e) {
            log.error("DAOException was caught. Cause : "+ e);
            return 0;
        }
    }
}

