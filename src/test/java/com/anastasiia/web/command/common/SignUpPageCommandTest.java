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

class SignUpPageCommandTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    UserService userService;
    SignUpPageCommand signUpPageCommand;

    @BeforeEach
    void setUp() {
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        userService = mock(UserService.class);
        signUpPageCommand = new SignUpPageCommand();
    }

    @AfterEach
    void tearDown() {
        response = null;
        request = null;
        userService = null;
        session = null;
        signUpPageCommand = null;
    }

    @Test
    void execute() {
        when(request.getSession()).thenReturn(session);
        CommandResult result = signUpPageCommand.execute(request, response);
        assertEquals(Pages.SIGN_UP, result.getPage());
    }
}