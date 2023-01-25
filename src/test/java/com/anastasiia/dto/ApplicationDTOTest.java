package com.anastasiia.dto;

import com.anastasiia.entity.Application;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationDTOTest {

    ApplicationDTO applicationDTO;
    @BeforeEach
    void setUp() {
        applicationDTO = new ApplicationDTO();
        applicationDTO.setId(1);
        applicationDTO.setUserDTO(new UserDTO());
        applicationDTO.setNumberOfGuests(2);
        applicationDTO.setClassOfRoom(ClassOfRoom.LUX);
        applicationDTO.setLengthOfStay(2);
        applicationDTO.setStatus(Status.NEW);
    }

    @Test
    void dtoToEntity() {
        Application application = applicationDTO.dtoToEntity();
        assertEquals(Application.class, application.getClass());
    }

    @Test
    void entityToDTO() {
        ApplicationDTO applicationDTO1 = applicationDTO.entityToDTO(new Application());
        assertEquals(ApplicationDTO.class, applicationDTO1.getClass());

    }
}