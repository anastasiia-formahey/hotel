package com.anastasiia.web.command.common;

import com.anastasiia.services.impl.UserService;
import com.anastasiia.utils.Pages;
import com.anastasiia.web.command.CommandResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AboutPageCommandTest {
    HttpServletResponse response;
    HttpServletRequest request;
    UserService userService;
    AboutPageCommand aboutPageCommand;

    @BeforeEach
    void setUp() {
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        userService = mock(UserService.class);
        aboutPageCommand = new AboutPageCommand();
    }

    @AfterEach
    void tearDown() {
        response = null;
        request = null;
        userService = null;
        aboutPageCommand = null;
    }

    @Test
    void execute() {
        CommandResult result = aboutPageCommand.execute(request, response);
        assertEquals(Pages.ABOUT, result.getPage());

    }
}