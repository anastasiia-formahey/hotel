package com.anastasiia.services;

import com.anastasiia.dao.ApplicationDAO;
import com.anastasiia.dao.DBManager;
import com.anastasiia.dao.Fields;
import com.anastasiia.dao.UserDAO;
import com.anastasiia.dto.ApplicationDTO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.entity.Application;
import com.anastasiia.entity.User;
import com.anastasiia.exceptions.DAOException;
import com.anastasiia.exceptions.ServiceException;
import com.anastasiia.utils.ClassOfRoom;
import com.anastasiia.utils.Role;
import com.anastasiia.utils.Status;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ApplicationServiceTest {
    UserDAO userDAO;
    ApplicationDAO applicationDAO;
    ApplicationService applicationService;
    @Before
    public void setUp() throws Exception {
    }



    private PreparedStatement prepareMock(DataSource dataSource) throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(isA(int.class), isA(int.class));
        doNothing().when(preparedStatement).setLong(isA(int.class), isA(long.class));
        doNothing().when(preparedStatement).setString(isA(int.class), isA(String.class));
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(connection).commit();
        return preparedStatement;
    }
    private PreparedStatement prepareMockThrowException(DataSource dataSource) throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
        doThrow(SQLException.class).when(preparedStatement).setInt(isA(int.class), isA(int.class));
        doThrow(SQLException.class).when(preparedStatement).setLong(isA(int.class), isA(long.class));
        doThrow(SQLException.class).when(preparedStatement).setString(isA(int.class), isA(String.class));
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(connection).commit();
        return preparedStatement;
    }

    private void resultSetMock(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("client_id")).thenReturn(1);
        when(resultSet.getInt("number_of_guests")).thenReturn(2);
        when(resultSet.getString("apartment_class")).thenReturn(ClassOfRoom.BUSINESS.name());
        when(resultSet.getString("length_of_stay")).thenReturn("2");
        when(resultSet.getString("status")).thenReturn(Status.NEW.name());
        when(resultSet.getInt(Fields.USER_ID)).thenReturn(1);
        when(resultSet.getString(Fields.USER_FIRST_NAME)).thenReturn("Test");
        when(resultSet.getString(Fields.USER_LAST_NAME)).thenReturn("Test");
        when(resultSet.getString(Fields.USER_EMAIL)).thenReturn("test@gmail.com");
        when(resultSet.getString(Fields.USER_PASSWORD)).thenReturn("0000");
        when(resultSet.getString(Fields.USER_ROLE)).thenReturn(Role.CLIENT.name());
    }
    @Test
    void dtoToEntity() {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setFirstName("Test");
        userDTO.setLastName("Test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setRole(Role.CLIENT);
        applicationDTO.setId(1);
        applicationDTO.setUserDTO(userDTO);
        applicationDTO.setClassOfRoom(ClassOfRoom.LUX);
        applicationDTO.setNumberOfGuests(2);
        applicationDTO.setLengthOfStay(2);
        applicationDTO.setStatus(Status.NEW);
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            applicationService = new ApplicationService();
            assertEquals("Application{id=1, clientId=1, numberOfGuests=2, classOfRoom=LUX, lengthOfStay=2}",
                    applicationService.dtoToEntity(applicationDTO).toString());
        }

    }
    @Test
    void entityToDTO() throws ServiceException {
        Application application = new Application(1,1,2,ClassOfRoom.LUX, 2);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setFirstName("Test");
        userDTO.setLastName("Test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setRole(Role.CLIENT);
        userDAO = mock(UserDAO.class);
    }

    @Test
    void insertApplication() throws SQLException{
        ApplicationDTO applicationDTO = new ApplicationDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setFirstName("Test");
        userDTO.setLastName("Test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setRole(Role.CLIENT);
        applicationDTO.setId(1);
        applicationDTO.setUserDTO(userDTO);
        applicationDTO.setClassOfRoom(ClassOfRoom.LUX);
        applicationDTO.setNumberOfGuests(2);
        applicationDTO.setLengthOfStay(2);
        applicationDTO.setStatus(Status.NEW);
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)){
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            applicationService = new ApplicationService();
            try(PreparedStatement ignored = prepareMock(dataSource)){
                assertDoesNotThrow(()->
                    applicationService.insertApplication(applicationDTO));
            }
            try(PreparedStatement ignored = prepareMockThrowException(dataSource)){
                assertThrows(ServiceException.class, ()->
                        applicationService.insertApplication(applicationDTO));
            }
        }
    }

    @Test
    void applicationCountAll() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            applicationService = new ApplicationService();
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                when(resultSet.next()).thenReturn(true).thenReturn(false);
                when(resultSet.getInt("COUNT(id)")).thenReturn(5);
                assertDoesNotThrow(() ->
                        applicationService.applicationCountAll());
            }
            try (PreparedStatement preparedStatement = prepareMockThrowException(dataSource)) {
                doThrow(SQLException.class).when(preparedStatement).executeQuery();
                assertEquals(0,
                        applicationService.applicationCountAll());
            }
        }
    }


//    @Test
//    void testSelectAll3() throws SQLException, ServiceException, DAOException {
//        User user = new User("Test", "Test", "test@gmail.com", "MTExMQ==", Role.CLIENT);
//        user.setId(1);
//
//        ApplicationDTO applicationDTO = new ApplicationDTO();
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(1);
//        userDTO.setFirstName("Test");
//        userDTO.setLastName("Test");
//        userDTO.setEmail("test@gmail.com");
//        userDTO.setRole(Role.CLIENT);
//        applicationDTO.setId(1);
//        applicationDTO.setUserDTO(userDTO);
//        applicationDTO.setClassOfRoom(ClassOfRoom.LUX);
//        applicationDTO.setNumberOfGuests(2);
//        applicationDTO.setLengthOfStay(2);
//        applicationDTO.setStatus(Status.NEW);
//        DataSource dataSource = mock(DataSource.class);
//        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
//            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
//            applicationService = new ApplicationService();
//            ApplicationService applicationServiceMock = mock(ApplicationService.class);
//            UserService userService = mock(UserService.class);
//            userDAO = mock(UserDAO.class);
//            Application application = new Application(1,1,2,ClassOfRoom.LUX, 2);
//
//
//            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
//                when(userDAO.findUserById(anyInt())).thenReturn(user);
//
//                ResultSet resultSet = mock(ResultSet.class);
//                when(preparedStatement.executeQuery()).thenReturn(resultSet);
//                resultSetMock(resultSet);
//                assertDoesNotThrow(() ->
//                        applicationService.selectAll(1));
//            }
//            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
//                ResultSet resultSet = mock(ResultSet.class);
//                doThrow(SQLException.class).when(preparedStatement).executeQuery();
//                assertEquals(0,
//                        applicationService.selectAll(1).size());
//            }
//        }
//    }
//
//    @Test
//    void testSelectAll4() throws DAOException {
//        assertDoesNotThrow(()->
//                applicationService.selectAll(1,5, "id"));
//    }
//
//    @Test
//    void testSelectAll5(){
//        DataSource dataSource = mock(DataSource.class);
//        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
//            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
//            applicationService = new ApplicationService();
//            assertDoesNotThrow(() ->
//                    applicationService.selectAll(1, 5, "id", 1));
//        }
//    }

    @Test
    void testGet() {
        ArrayList<ApplicationDTO> applicationDTOS = new ArrayList<>();
        ApplicationDTO applicationDTO = new ApplicationDTO();
        ApplicationDTO applicationDTO1 = new ApplicationDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setFirstName("Test");
        userDTO.setLastName("Test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setRole(Role.CLIENT);
        applicationDTO.setId(1);
        applicationDTO.setUserDTO(userDTO);
        applicationDTO.setClassOfRoom(ClassOfRoom.LUX);
        applicationDTO.setNumberOfGuests(2);
        applicationDTO.setLengthOfStay(2);
        applicationDTO.setStatus(Status.NEW);
        applicationDTO1.setId(2);
        applicationDTO1.setUserDTO(userDTO);
        applicationDTO1.setClassOfRoom(ClassOfRoom.LUX);
        applicationDTO1.setNumberOfGuests(2);
        applicationDTO1.setLengthOfStay(2);
        applicationDTO1.setStatus(Status.NEW);
        applicationDTOS.add(applicationDTO);
        applicationDTOS.add(applicationDTO1);
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            applicationService = new ApplicationService();
            assertEquals("ApplicationDTO{id=1, userDTO=UserDTO{id=1, firstName='Test', lastName='Test', email='test@gmail.com', role=CLIENT}, numberOfGuests=2, classOfRoom=LUX, lengthOfStay=2, status=NEW}",
                    applicationService.get(applicationDTOS, 1).toString());
        }
    }

    @Test
    void applicationCountByStatus() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        try (MockedStatic<DBManager> dbManagerMockedStatic = mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dataSource);
            applicationService = new ApplicationService();
            try (PreparedStatement preparedStatement = prepareMock(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                when(resultSet.next()).thenReturn(true).thenReturn(false);
                when(resultSet.getInt("COUNT(id)")).thenReturn(5);
                assertDoesNotThrow(() ->
                        applicationService.applicationCountByStatus(Status.NEW));
            }
            try (PreparedStatement preparedStatement = prepareMockThrowException(dataSource)) {
                ResultSet resultSet = mock(ResultSet.class);
                when(preparedStatement.executeQuery()).thenReturn(resultSet);
                when(resultSet.next()).thenReturn(true).thenReturn(false);
                when(resultSet.getInt("COUNT(id)")).thenReturn(5);
                assertEquals(0,
                        applicationService.applicationCountByStatus(Status.NEW));
            }
        }
    }
}