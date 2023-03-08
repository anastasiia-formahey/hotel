package com.anastasiia.exceptions;

public class InvalidUserException extends ServiceException{

    public InvalidUserException(String message) {
        super(message);
    }

    public InvalidUserException(Throwable cause) {
        super(cause);
    }
}
