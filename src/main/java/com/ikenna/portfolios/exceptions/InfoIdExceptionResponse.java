package com.ikenna.portfolios.exceptions;

public class InfoIdExceptionResponse {
    private String phone;

    public InfoIdExceptionResponse(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
