package com.anastasiia.services;

import com.anastasiia.exceptions.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ValidationTest {

    Validation validation;
    @BeforeEach
    void setUp() {
        validation = new Validation();
    }

    @Test
    void validEmailTrue() throws ValidationException {
       String result = validation.validEmail("test@gmail.com");
        assertEquals("test@gmail.com", result);
    }

    @Test
    void validEmailFalse() throws ValidationException {
        String result = validation.validEmail("@testgmail.com");
        assertEquals("@testgmail.com", result);
    }
    @Test
    void validEmailFalse1() throws ValidationException {
        String result = validation.validEmail(null);
        assertEquals(null, result);
    }

    @Test
    void validPasswordTrue() throws ValidationException {
        String result = validation.validPassword("0000");
        assertEquals("0000", result);
    }
    @Test
    void validPasswordFalse() throws ValidationException {
        String result = validation.validPassword(null);
        assertEquals(false, result);
    }
    @Test
    void validPasswordFalse1() throws ValidationException {
        String result = validation.validPassword("");
        assertEquals(false, result);
    }

    @Test
    void validField() throws ValidationException {
        assertDoesNotThrow(()-> validation.validField("Test"));
        assertThrows(ValidationException.class, ()-> Validation.validField(""));
        assertThrows(ValidationException.class, ()-> Validation.validField(null));
    }

    @Test
    void validField1() throws ValidationException {
        assertDoesNotThrow(()-> validation.validField(Date.valueOf("2023-03-06"),Date.valueOf("2023-03-07")));
        assertThrows(ValidationException.class, ()-> Validation.validField(Date.valueOf("2023-03-07"),Date.valueOf("2023-03-06")));
        assertThrows(ValidationException.class, ()-> Validation.validField(Date.valueOf("2023-03-06"),Date.valueOf("2023-03-06")));
    }

    @AfterEach
    void tearDown() {
        validation = null;
    }
}