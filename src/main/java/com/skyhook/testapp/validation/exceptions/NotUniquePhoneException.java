package com.skyhook.testapp.validation.exceptions;

public class NotUniquePhoneException extends NotUniqueFieldException {

    private static final String message = "Phone number must be unique, this number is already taken";

    public NotUniquePhoneException() {
        super(message);
    }
}
