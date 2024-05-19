package com.donation.annadanam.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiException apiException = new ApiException(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                ex
        );
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }
}