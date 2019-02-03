package com.skyhook.testapp.validation.exceptions;

public class NotUniqueEmailException extends NotUniqueFieldException {

    private static final String message = "Email must be unique, this email is already taken";

    public NotUniqueEmailException() {
        super(message);
    }
}
