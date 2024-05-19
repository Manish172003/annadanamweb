package com.donation.annadanam.exception;

import org.springframework.http.HttpStatus;

public class ApiException {

    private final HttpStatus status;
    private final String message;
    private final Throwable throwable;

    public ApiException(HttpStatus status, String message, Throwable throwable) {
        this.status = status;
        this.message = message;
        this.throwable = throwable;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}