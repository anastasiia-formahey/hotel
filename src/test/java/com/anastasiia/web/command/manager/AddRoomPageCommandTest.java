package com.anastasiia.web.command.manager;

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

class AddRoomPageCommandTest {
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    AddRoomPageCommand addRoomPageCommand;

    @BeforeEach
    void setUp() {
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        addRoomPageCommand = new AddRoomPageCommand();
    }

    @AfterEach
    void tearDown() {
        response = null;
        request = null;
        session = null;
        addRoomPageCommand = null;
    }

    @Test
    void execute() {
        when(request.getSession()).thenReturn(session);
        CommandResult commandResult = addRoomPageCommand.execute(request, response);
        assertEquals(Pages.MANAGER_ADD_ROOM, commandResult.getPage());
    }
}