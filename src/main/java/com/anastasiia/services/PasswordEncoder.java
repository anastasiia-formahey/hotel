package com.anastasiia.services;

import java.util.Base64;

/**
 * PasswordEncoder class uses java.util.Base64 to encode user password
 */
public class PasswordEncoder {
    public static String encode(String password){
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
