package com.anastasiia.services;

import com.anastasiia.dao.ApplicationDAO;
import com.anastasiia.dao.DBManager;
import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.Application;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Fields;
import com.anastasiia.utils.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
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