package com.skyhook.testapp.validation.exceptions;

public class NotUniqueFieldException extends RuntimeException {

    public NotUniqueFieldException() {
    }

    public NotUniqueFieldException(String message) {
        super(message);
    }
}
