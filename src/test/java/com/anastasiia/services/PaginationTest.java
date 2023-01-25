package com.anastasiia.services;

import com.anastasiia.web.command.common.LoginPageCommand;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PaginationTest {
    HttpServletRequest request;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
    }

    @AfterEach
    void tearDown() {
        request = null;
    }

    @Test
    void setPagination() {

    }

    @Test
    void getInt() {
        int result = Pagination.getInt("1");
        assertEquals(1, result);
    }
    @Test
    void getInt1() {
        //catch NumberFormatException
        assertEquals(-1, Pagination.getInt("ll"));
    }
    @Test
    void getCurrentPage() {
        assertEquals(1, Pagination.getInt("1"));
    }

    @Test
    void getCurrentPage1() {
        assertEquals(-1, Pagination.getInt("ll"));
    }
    @Test
    void getCurrentPag2() {
        assertEquals(-1, Pagination.getInt(null));
    }
}