package com.ikenna.portfolios.exceptions;

public class WorkExceptionResponse {

    private String identifier;


    public WorkExceptionResponse(String identifier) {
        this.identifier = identifier;

    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
