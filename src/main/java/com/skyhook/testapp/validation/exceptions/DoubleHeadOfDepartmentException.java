package com.skyhook.testapp.validation.exceptions;

public class DoubleHeadOfDepartmentException extends RuntimeException {
    public DoubleHeadOfDepartmentException() {
    }

    public DoubleHeadOfDepartmentException(String message) {
        super(message);
    }
}
