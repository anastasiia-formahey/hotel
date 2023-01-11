package com.anastasiia.services;

public class LocaleMessage {
    public static String invalidLoginOrPassword(String locale){
        String message = "Incorrect data";
        switch (locale){
            case "en" : {
                message = "Incorrect email or password";
                break;
            }
            case "ua": {
                message = "Невірно введені електронна пошта або пароль";
            break;
            }
        }
        return message;
    }
}
