package com.etnetera.hr.exceptions;

public class ServiceException extends RuntimeException {

    public ServiceException(String errorMessage) {
        super(errorMessage);
    }
}
