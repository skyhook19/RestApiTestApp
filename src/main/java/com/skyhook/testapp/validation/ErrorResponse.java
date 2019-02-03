package com.skyhook.testapp.validation;

import java.util.Date;
import java.util.List;

public class ErrorResponse {
    private Date timestamp;
    private List<String> errorMessages;
    private String details;

    public ErrorResponse(Date timestamp, List<String> errorMessages, String details) {
        this.timestamp = timestamp;
        this.errorMessages = errorMessages;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public String getDetails() {
        return details;
    }
}
