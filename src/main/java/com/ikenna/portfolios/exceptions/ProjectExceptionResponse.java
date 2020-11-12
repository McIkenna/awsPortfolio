package com.ikenna.portfolios.exceptions;

public class ProjectExceptionResponse {

    private String projectTitle;

    public ProjectExceptionResponse(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
}
