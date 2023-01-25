package com.anastasiia.services;

import com.anastasiia.dto.RequestDTO;
import com.anastasiia.dto.RoomDTO;
import com.anastasiia.entity.Request;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestServiceTest {
    RequestService requestService;
    RequestDTO requestDTO;
    RoomDTO roomDTO;
    Request request;

    @BeforeEach
    void setUp() {
        requestService = new RequestService();
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
        request.setApplicationId(2);
        request.setCheckInDate(Date.valueOf("2023-02-25"));
        request.setCheckOutDate(Date.valueOf("2023-02-27"));
        request.setRoomId(1);
        request.setStatus(Status.NOT_CONFIRMED);
        List <Request> requests = new ArrayList<>();
        requests.add(request);
        assertEquals(requests.toString(), requestService.dtoToEntity(requestDTO).toString());
    }

    @AfterEach
    void tearDown() {
        requestService = null;
        roomDTO = null;
    }

    @Test
    void entityToDTO() {

    }
}