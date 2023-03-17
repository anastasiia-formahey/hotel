package com.anastasiia.web.command.common;

import com.anastasiia.services.UserService;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.CommandResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginPageCommandTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    LoginPageCommand loginPageCommand;
    @BeforeEach
    void setUp() {
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        loginPageCommand = new LoginPageCommand();
    }
    @Test
    void execute() {
        when(request.getSession()).thenReturn(session);
        CommandResult result = loginPageCommand.execute(request, response);
        assertEquals(Pages.LOGIN, result.getPage());
    }
    @AfterEach
    void tearDown() {
        response = null;
        request = null;
        loginPageCommand = null;
    }
}