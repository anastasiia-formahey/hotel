package com.anastasiia.services;

import com.anastasiia.dao.DBManager;
import com.anastasiia.dto.RequestDTO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.entity.Request;
import com.anastasiia.services.impl.RequestService;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class RequestServiceTest {
    RequestService requestService;
    RequestDTO requestDTO;
    RoomDTO roomDTO;
    Request request;

    @BeforeEach
    void setUp() {
        roomDTO = new RoomDTO();
        roomDTO.setId(1);
        roomDTO.setClassOfRoom(ClassOfRoom.LUX);
        roomDTO.setNumberOfPerson(2);
        roomDTO.setImage("lux.jpg");
        roomDTO.setPrice(1000);
        requestDTO = new RequestDTO();
        requestDTO.setApplicationId(2);
        requestDTO.setStatusOfRequest(Status.NOT_CONFIRMED);
        requestDTO.setRequestElements(roomDTO, Date.valueOf("2023-02-25"), Date.valueOf("2023-02-27"));
        request = new Request();
    }
    @Test
    void dtoToEntity() {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            requestService = new RequestService();
            request.setApplicationId(2);
            request.setCheckInDate(Date.valueOf("2023-02-25"));
            request.setCheckOutDate(Date.valueOf("2023-02-27"));
            request.setRoomId(1);
            request.setStatus(Status.NOT_CONFIRMED);
            List<Request> requests = new ArrayList<>();
            requests.add(request);
            assertEquals(requests.toString(), requestService.dtoToEntity(requestDTO).toString());
        }
    }

    @AfterEach
    void tearDown() {
        requestService = null;
        roomDTO = null;
    }

    @Test
    void getRequestByApplicationId() {

    }

    @Test
    void updateStatus() {
    }
}