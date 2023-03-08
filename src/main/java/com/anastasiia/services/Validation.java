package com.anastasiia.services;

import com.anastasiia.exceptions.ValidationException;
import com.anastasiia.utils.JspAttributes;

import java.sql.Date;
import java.util.Calendar;
import java.util.regex.Pattern;

public class Validation {

    public static final String EMAIL_REGEX = "[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.[a-z]{2,4}";
    public static final String PASSWORD_REGEX = "[A-Za-z0-9._-]{4,20}";
    public static final String DATE_REGEX = "\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])*";

    public static String validEmail(String email) throws ValidationException {
        boolean result;
        try {
            if(email.equals("")) throw new ValidationException(JspAttributes.EMAIL_EXCEPTION);
            result = Pattern.matches(EMAIL_REGEX, email);
            if(result){
                return email;
            }else throw new ValidationException(JspAttributes.INVALID_EMAIL_EXCEPTION);
        }catch (NullPointerException e){
            throw new ValidationException(JspAttributes.EMAIL_EXCEPTION);
        }
    }

    public static String validPassword(String password) throws ValidationException {
        boolean result;
        try {
            if(password.equals("")) throw new ValidationException(JspAttributes.PASSWORD_EXCEPTION);
            result = Pattern.matches(PASSWORD_REGEX, password);
            if(result){
                return password;
            }else throw new ValidationException(JspAttributes.INVALID_PASSWORD_EXCEPTION);
        }catch (NullPointerException e){
            throw new ValidationException(JspAttributes.PASSWORD_EXCEPTION);
        }

    }
    public static String validField(String field) throws ValidationException {
        try {
            if(field.equals("")) throw new ValidationException(JspAttributes.FIELD_EXCEPTION);
            else return field;
        }catch (NullPointerException e){
            throw new ValidationException(JspAttributes.FIELD_EXCEPTION);
        }
    }
    public static int validIntField(String field) throws ValidationException {
        try {
            if(field.equals("")) throw new ValidationException(JspAttributes.FIELD_EXCEPTION);
            if(Integer.parseInt(field) <= 0 ) throw new ValidationException(JspAttributes.NUMBER_EXCEPTION);
            else return Integer.parseInt(field);
        }catch (NullPointerException | NumberFormatException e){
            throw new ValidationException(JspAttributes.NUMBER_EXCEPTION);
        }
    }
    public static Date validDate(String date) throws ValidationException {
        try {
            if(date.equals("") || !Pattern.matches(DATE_REGEX, date))
                throw new ValidationException(JspAttributes.INVALID_DATE_EXCEPTION);
        }catch (NullPointerException e){
            throw new ValidationException(JspAttributes.FIELD_EXCEPTION);
        }
        return Date.valueOf(date);
    }

    public static void validField(Date checkIn, Date checkOut) throws ValidationException {
        try {
            if(!Pattern.matches(DATE_REGEX, checkIn.toString()) && !Pattern.matches(DATE_REGEX, checkOut.toString()))
                throw new ValidationException(JspAttributes.INVALID_DATE_EXCEPTION);
            if(!checkIn.after(new Date(Calendar.getInstance().getTime().getTime())))
                throw new ValidationException(JspAttributes.INCORRECT_BOOK_DATE_EXCEPTION);
            if(checkIn.after(checkOut) || checkIn.compareTo(checkOut) >= 0)
                throw new ValidationException(JspAttributes.INVALID_DATE_EXCEPTION);
        }catch (NullPointerException e){
            throw new ValidationException(JspAttributes.FIELD_EXCEPTION);
        }
    }

    public static void validDateToCheckIn(Date checkIn, Date checkOut) throws ValidationException{
        if(checkIn.compareTo(new Date(Calendar.getInstance().getTime().getTime())) <1)
            throw new ValidationException(JspAttributes.CHECK_IN_EXCEPTION);
        if(checkOut.compareTo(new Date(Calendar.getInstance().getTime().getTime())) >=1)
            throw new ValidationException(JspAttributes.CHECK_IN_EXCEPTION);
    }
}
