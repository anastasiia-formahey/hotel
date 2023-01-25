package com.anastasiia.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocaleMessageTest {

    @Test
    void invalidLoginOrPassword() {
        assertEquals("Incorrect email or password",
                LocaleMessage.invalidLoginOrPassword("en"));
        assertEquals("Невірно введені електронна пошта або пароль",
                LocaleMessage.invalidLoginOrPassword("ua"));
        assertEquals("Incorrect data",
                LocaleMessage.invalidLoginOrPassword(""));
    }
}