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
        assertThrows(ValidationException.class, ()-> validation.validEmail("@testgmail.com"));
    }
    @Test
    void validEmailFalse1() throws ValidationException {
        assertThrows(ValidationException.class, ()-> validation.validEmail(null));
    }

    @Test
    void validPasswordTrue() throws ValidationException {
        String result = validation.validPassword("0000");
        assertEquals("0000", result);
    }
    @Test
    void validPasswordFalse() throws ValidationException {
        assertThrows(ValidationException.class, ()-> validation.validPassword(null));
    }
    @Test
    void validPasswordFalse1() throws ValidationException {
        assertThrows(ValidationException.class, ()-> validation.validPassword(""));
    }

    @Test
    void validField() throws ValidationException {
        assertDoesNotThrow(()-> validation.validField("Test"));
        assertThrows(ValidationException.class, ()-> Validation.validField(""));
        assertThrows(ValidationException.class, ()-> Validation.validField(null));
    }

    @Test
    void validField1() throws ValidationException {
        assertDoesNotThrow(()-> Validation.validField(Date.valueOf("2024-03-10"),Date.valueOf("2024-03-11")));
        assertThrows(ValidationException.class, ()-> Validation.validField(Date.valueOf("2023-03-07"),Date.valueOf("2023-03-06")));
        assertThrows(ValidationException.class, ()-> Validation.validField(Date.valueOf("2023-03-07"),Date.valueOf("2023-03-06")));
    }

    @Test
    void validIntField() throws ValidationException {
        assertThrows(ValidationException.class, ()-> Validation.validIntField(""));
        assertThrows(ValidationException.class, ()-> Validation.validIntField(null));
        assertThrows(ValidationException.class, ()-> Validation.validIntField("0"));
        assertEquals(1, Validation.validIntField("1"));
    }

    @Test
    void validDate() throws ValidationException {
        assertThrows(ValidationException.class, ()-> Validation.validDate(""));
        assertThrows(ValidationException.class, ()-> Validation.validDate(null));
        assertThrows(ValidationException.class, ()-> Validation.validDate("02.02.2023"));
        assertEquals(Date.valueOf("2023-03-08"), Validation.validDate("2023-03-08"));
    }

    @Test
    void validDateToCheckIn() {
        assertDoesNotThrow(()-> Validation.validDateToCheckIn(Date.valueOf("2024-03-08"),Date.valueOf("2024-03-09")));
        assertThrows(ValidationException.class, ()-> Validation.validDateToCheckIn(Date.valueOf("2023-03-07"),Date.valueOf("2023-03-08")));
        assertThrows(ValidationException.class, ()-> Validation.validDateToCheckIn(Date.valueOf("2023-03-07"),Date.valueOf("2023-03-08")));

    }

    @AfterEach
    void tearDown() {
        validation = null;
    }
}