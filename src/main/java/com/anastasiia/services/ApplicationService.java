package com.anastasiia.services;

import com.anastasiia.dao.ApplicationDAO;
import com.anastasiia.dao.DBManager;
import com.anastasiia.dto.ApplicationDTO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationService {
    private static final Logger log = Logger.getLogger(ApplicationService.class);
    private final ApplicationDAO applicationDAO = new ApplicationDAO(DBManager.getInstance());

    public void insertApplication(ApplicationDTO applicationDTO){
        try {
            applicationDAO.insertApplication(applicationDTO.dtoToEntity());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public List<ApplicationDTO> selectAll(){
        try {
            return applicationDAO.selectAllApplications()
                    .stream().map(new ApplicationDTO()::entityToDTO).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ApplicationDTO> selectAll(int id){
        return applicationDAO.selectAllApplications(id)
                .stream().map(new ApplicationDTO()::entityToDTO).collect(Collectors.toList());
    }
    public List<ApplicationDTO> selectAll(int currentPage, int recordsPerPage, String orderBy){
        currentPage = currentPage * Pagination.RECORDS_PER_PAGE - recordsPerPage;
        return applicationDAO.selectAllApplications(currentPage,recordsPerPage,orderBy)
                .stream().map(new ApplicationDTO()::entityToDTO).collect(Collectors.toList());
    }
    public List<ApplicationDTO> selectAll(int currentPage, int recordsPerPage, String orderBy, int id){
        currentPage = currentPage * Pagination.RECORDS_PER_PAGE - recordsPerPage;
        return applicationDAO.selectAllApplications(currentPage,recordsPerPage,orderBy,id)
                .stream().map(new ApplicationDTO()::entityToDTO).collect(Collectors.toList());
    }
    public ApplicationDTO get(List<ApplicationDTO> applicationDTOList, int id){
        applicationDTOList.forEach(log::debug);
        return applicationDTOList.stream()
                .filter(applicationDTO -> id==(applicationDTO.getId()))
                .findAny().orElse(new ApplicationDTO());
    }
}
