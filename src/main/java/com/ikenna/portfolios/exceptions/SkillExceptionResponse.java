package com.ikenna.portfolios.exceptions;

public class SkillExceptionResponse {

    private String skillname;

    public SkillExceptionResponse(String skillname) {
        this.skillname = skillname;
    }

    public String getSkillname() {
        return skillname;
    }

    public void setSkillname(String skillname) {
        this.skillname = skillname;
    }
}
