package com.anastasiia.dto;

import com.anastasiia.entity.User;
import com.anastasiia.utils.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    UserDTO userDTO;
    User user;
    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setFirstName("Name");
        userDTO.setLastName("Name");
        userDTO.setEmail("test@gmail.com");
        userDTO.setRole(Role.CLIENT);
        user = new User();
        user.setId(1);
        user.setFirstName("Name");
        user.setLastName("Name");
        user.setEmail("test@gmail.com");
        user.setRole(Role.CLIENT);
    }

    @AfterEach
    void tearDown() {
        userDTO = null;
        user = null;
    }

    @Test
    void entityToDTO() {
        assertEquals(UserDTO.class, userDTO.entityToDTO(user).getClass());
    }

    @Test
    void dtoToEntity() {
        assertEquals(User.class, userDTO.dtoToEntity().getClass());
    }
}