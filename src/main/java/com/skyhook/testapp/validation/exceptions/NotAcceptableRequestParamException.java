package com.skyhook.testapp.validation.exceptions;

public class NotAcceptableRequestParamException extends RuntimeException {
    public NotAcceptableRequestParamException() {
    }

    public NotAcceptableRequestParamException(String message) {
        super(message);
    }
}
