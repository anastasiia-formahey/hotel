package com.anastasiia.services;

import com.anastasiia.dao.ApplicationDAO;
import com.anastasiia.dto.ApplicationDTO;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class ApplicationService {
    private static final Logger log = Logger.getLogger(ApplicationService.class);

    public void insertApplication(ApplicationDTO applicationDTO){
        ApplicationDAO.getInstance().insertApplication(applicationDTO.dtoToEntity());
    }
    public List<ApplicationDTO> selectAll(){
        return ApplicationDAO.getInstance().selectAllApplications()
                .stream().map(new ApplicationDTO()::entityToDTO).collect(Collectors.toList());
    }

    public List<ApplicationDTO> selectAll(int id){
        return ApplicationDAO.getInstance().selectAllApplications(id)
                .stream().map(new ApplicationDTO()::entityToDTO).collect(Collectors.toList());
    }
    public List<ApplicationDTO> selectAll(int currentPage, int recordsPerPage, String orderBy){
        currentPage = currentPage * Pagination.RECORDS_PER_PAGE - recordsPerPage;
        return ApplicationDAO.getInstance().selectAllApplications(currentPage,recordsPerPage,orderBy)
                .stream().map(new ApplicationDTO()::entityToDTO).collect(Collectors.toList());
    }
    public List<ApplicationDTO> selectAll(int currentPage, int recordsPerPage, String orderBy, int id){
        currentPage = currentPage * Pagination.RECORDS_PER_PAGE - recordsPerPage;
        return ApplicationDAO.getInstance().selectAllApplications(currentPage,recordsPerPage,orderBy,id)
                .stream().map(new ApplicationDTO()::entityToDTO).collect(Collectors.toList());
    }
    public ApplicationDTO get(List<ApplicationDTO> applicationDTOList, int id){
        return applicationDTOList.stream()
                .filter(applicationDTO -> id==(applicationDTO.getId()))
                .findAny().orElse(new ApplicationDTO());
    }
}
