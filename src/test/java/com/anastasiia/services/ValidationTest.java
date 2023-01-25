package com.anastasiia.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

    Validation validation;
    @BeforeEach
    void setUp() {
        validation = new Validation();
    }

    @Test
    void validEmailTrue() {
       boolean result = validation.validEmail("test@gmail.com");
        assertEquals(true, result);
    }

    @Test
    void validEmailFalse() {
        boolean result = validation.validEmail("@testgmail.com");
        assertEquals(false, result);
    }
    @Test
    void validEmailFalse1() {
        boolean result = validation.validEmail(null);
        assertEquals(false, result);
    }

    @Test
    void validPasswordTrue() {
        boolean result = validation.validPassword("0000");
        assertEquals(true, result);
    }
    @Test
    void validPasswordFalse() {
        boolean result = validation.validPassword(null);
        assertEquals(false, result);
    }
    @Test
    void validPasswordFalse1() {
        boolean result = validation.validPassword("");
        assertEquals(false, result);
    }

    @AfterEach
    void tearDown() {
        validation = null;
    }
}