package com.garde.christopher.challenge.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HTTPBadRequestException extends RuntimeException {
    public HTTPBadRequestException(String message) {
        super(message);
    }
}
