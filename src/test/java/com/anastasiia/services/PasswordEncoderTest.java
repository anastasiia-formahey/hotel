package com.anastasiia.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {

    @Test
    void encode() {
        String result = PasswordEncoder.encode("1111");
        assertEquals(result, "MTExMQ==");
    }
}