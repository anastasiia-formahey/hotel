package com.anastasiia.services;

import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.entity.Application;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.utils.Status;

import java.util.List;

/**
 * The interface implements behavior for ApplicationService
 */
public interface IApplicationService {
    /**
     * Method generates Application entity object from ApplicationDTO object
     * @param applicationDTO ApplicationDTO object
     * @return Application entity object
     */
    Application dtoToEntity(ApplicationDTO applicationDTO);

    /**
     * Method generates ApplicationDTO object from Application entity object
     * @param application Application entity object
     * @return ApplicationDTO object
     */
    ApplicationDTO entityToDTO(Application application);

    /**
     * Method generate Application entity and inserts this object
     * @param applicationDTO ApplicationDTO object
     */
    void insertApplication(ApplicationDTO applicationDTO) throws ServiceException;

    /**
     * Method selects all Application objects and generates list of ApplicationDTO objects
     * @return list of ApplicationDTO objects
     */
    int applicationCountAll();
    /**
     * Method selects all Application objects by user identity
     * @param id user identity
     * @return list of ApplicationDTO objects by specific user
     */
    List<ApplicationDTO> selectAll(int id);

    /**
     * Method selects Application objects from the table with limits.
     * This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param recordsPerPage number of records to select
     * @param orderBy parameter for sorting records
     * @return list of ApplicationDTO objects with certain number of records
     */
    List<ApplicationDTO> selectAll(int currentPage, int recordsPerPage, String orderBy);

    /**
     * Method selects Application objects from the table with limits by user identity.
     * This method uses to implement a pagination
     * @param currentPage start record to selecting
     * @param recordsPerPage number of records to select
     * @param orderBy parameter for sorting records
     * @param id user identity
     * @return list of ApplicationDTO objects with certain number of records by specific user
     */
    List<ApplicationDTO> selectAll(int currentPage, int recordsPerPage, String orderBy, int id);

    /**
     * Method selects certain ApplicationDTO object from the list by application identity
     * @param applicationDTOList list of ApplicationDTO objects
     * @param id application identity to select
     * @return ApplicationDTO object, returns empty ApplicationDTO object if list does not contain object with specified id
     */
    ApplicationDTO get(List<ApplicationDTO> applicationDTOList, int id);

    /**
     *
     * @param status object of class <Code>Status<Code/> (NEW, REVIEWED, CONFIRMED)
     * @return amount of records by specific <Code>status</Code>
     */
    int applicationCountByStatus(Status status);
}
