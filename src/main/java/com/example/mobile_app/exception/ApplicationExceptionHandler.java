package com.example.mobile_app.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEntityCreationException(EntityCreationException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.getErrors().put("error", ex.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        log.error("EntityCreationException: {}", ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.getErrors().put("error", ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        log.error("EntityNotFoundException: {}", ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.getErrors().put("error", ex.getMessage());
        log.error("IllegalArgumentException: {}", ex.getMessage());
        return errorResponse;
    }
}
