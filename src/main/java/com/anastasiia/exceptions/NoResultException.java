package com.anastasiia.exceptions;

public class NoResultException extends ServiceException{
    public NoResultException(String message) {
        super(message);
    }

    public NoResultException(Throwable cause) {
        super(cause);
    }
}
