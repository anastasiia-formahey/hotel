package com.anastasiia.exceptions;

public class ValidationException extends ServiceException{
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
