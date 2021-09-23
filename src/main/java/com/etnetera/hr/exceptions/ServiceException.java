package com.etnetera.hr.exceptions;

public class ServiceException extends RuntimeException {

    public static final String errorPrefix = "Service error -> ";

    /**
     * Specific exception for errors during data persistence and operations on service layer.
     * @param errorMessage error message
     */
    public ServiceException(String errorMessage) {
        super(errorPrefix + errorMessage);
    }
}
