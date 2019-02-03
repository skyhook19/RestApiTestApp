package com.skyhook.testapp.validation;

import com.skyhook.testapp.validation.exceptions.NotAcceptableRequestParamException;
import com.skyhook.testapp.validation.exceptions.NotUniqueFieldException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errorMessages = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String errorMessage = error.getField() + ": " + error.getDefaultMessage();
            errorMessages.add(errorMessage);
        }

        String description = "Your JSON must have correct field set";

        ErrorResponse exceptionResponse = new ErrorResponse(new Date(), errorMessages, description);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotUniqueFieldException.class)
    public final ResponseEntity<ErrorResponse> handleNotUniqueFieldException(NotUniqueFieldException ex, WebRequest webRequest) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(new Date(), errorMessages, "Use unique value");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAcceptableRequestParamException.class)
    public final ResponseEntity<ErrorResponse> handleNotAcceptableRequestParamException(NotAcceptableRequestParamException ex) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(new Date(), errorMessages, "Request param value must be appropriate");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
