package com.anastasiia.services;

import com.anastasiia.dto.RoomDTO;
import com.anastasiia.entity.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomServiceTest {
    RoomService roomService;

    @BeforeEach
    void setUp() {
        roomService = new RoomService();
    }

    @AfterEach
    void tearDown() {
        roomService = null;
    }

    @Test
    void getCapacityOfRoom() {
    }
    @Test
    void dtoToEntity() {
        assertEquals(Room.class, roomService.dtoToEntity(new RoomDTO()).getClass());
    }
}