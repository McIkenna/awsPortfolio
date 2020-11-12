package com.ikenna.portfolios.exceptions;

public class EducationExceptionResponse {
    private String schoolName;

    public EducationExceptionResponse(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
