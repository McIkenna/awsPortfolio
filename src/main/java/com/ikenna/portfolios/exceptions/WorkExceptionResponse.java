package com.ikenna.portfolios.exceptions;

public class WorkExceptionResponse {

    private String error;


    public WorkExceptionResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
