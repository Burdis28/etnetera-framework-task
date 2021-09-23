package com.etnetera.hr.exceptions;

public class FrameworkValidationException extends RuntimeException {

    public static final String errorPrefix = "Validation error -> ";

    public FrameworkValidationException(String errorMessage) {
        super(errorPrefix + errorMessage);
    }

}
