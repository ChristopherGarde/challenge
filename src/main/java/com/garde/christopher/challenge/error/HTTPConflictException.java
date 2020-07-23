package com.garde.christopher.challenge.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class HTTPConflictException extends RuntimeException {
    public HTTPConflictException(String message) {
        super(message);
    }
}
