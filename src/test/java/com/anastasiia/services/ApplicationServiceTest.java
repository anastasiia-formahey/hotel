package com.anastasiia.services;

import com.anastasiia.dto.ApplicationDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ApplicationServiceTest {
    DataSource dataSource;
    InitialContext initialContext = mock(InitialContext.class);
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    ApplicationDTO application;
    ApplicationService applicationService;

    @Test
    void selectAll() {
        //ApplicationDAO.getInstance().selectAllApplications();
    }

    @Test
    void testSelectAll() {
    }

    @Test
    void testSelectAll1() {
    }

    @Test
    void testSelectAll2() {
    }

    @Test
    void get() {
    }
}