package com.anastasiia.web.command.common;

import com.anastasiia.dao.UserDAO;
import com.anastasiia.dto.UserDTO;
import com.anastasiia.services.UserService;
import com.anastasiia.utils.JspAttributes;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.CommandResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginCommandTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    UserService userService;
    LoginCommand loginCommand;

    @BeforeEach
    void setUp() throws SQLException {
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        userService = mock(UserService.class);
        loginCommand = new LoginCommand();
    }
    @Test
    void execute() throws SQLException {
        when(request.getParameter(JspAttributes.EMAIL)).thenReturn("test@gmail.com");
        when(request.getParameter(JspAttributes.PASSWORD)).thenReturn("1111");

        CommandResult result = loginCommand.execute(request, response);

        assertEquals(Pages.LOGIN, result.getPage());

    }
    @AfterEach
    void tearDown() {
        response = null;
        request = null;
        session = null;
        userService = null;
        loginCommand = null;
    }


}