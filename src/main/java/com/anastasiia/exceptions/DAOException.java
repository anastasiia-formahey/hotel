package com.anastasiia.exceptions;

/**
 * DAOException class wrapper for SQLException
 */
public class DAOException extends Exception{

    public DAOException(Throwable cause) {
        super(cause);
    }
}
