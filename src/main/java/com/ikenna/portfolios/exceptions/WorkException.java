package com.ikenna.portfolios.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WorkException extends RuntimeException {
    public WorkException(String message) {
        super(message);
    }
}
