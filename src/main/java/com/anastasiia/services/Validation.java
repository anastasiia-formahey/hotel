package com.anastasiia.services;

import java.util.regex.Pattern;

public class Validation {

    public static final String EMAIL_REGEX = "[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.[a-z]{2,4}";
    public static final String PASSWORD_REGEX = "[A-Za-z0-9._-]{4,20}";

    public boolean validEmail(String email){
        boolean result;
        try {
        result = Pattern.matches(EMAIL_REGEX, email);
            return result;
        }catch (NullPointerException e){
            return false;
        }
    }

    public boolean validPassword(String password){
        boolean result;
        try {
            result = Pattern.matches(PASSWORD_REGEX, password);
            return result;
        }catch (NullPointerException e){
            return false;
        }

    }

}
