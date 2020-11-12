package com.ikenna.portfolios.infos;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //one to many with info
    @NotBlank(message = "must include project name")
    private String projectTitle;
    @NotBlank(message = "must include project Identifier")
    @Column(updatable = false, unique=true)
    private String projectIdentifier;
    @NotBlank(message = "must include role")
    private String keyRole;
    @NotBlank(message = "must include summary")
    private String projectSummary;
    @NotBlank(message = "must include progress")
    private String progress;
    private int progressrate;
    private Date startDate;
    private Date endDate;

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getKeyRole() {
        return keyRole;
    }

    public void setKeyRole(String keyRole) {
        this.keyRole = keyRole;
    }

    public String getProjectSummary() {
        return projectSummary;
    }

    public void setProjectSummary(String projectSummary) {
        this.projectSummary = projectSummary;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public int getProgressrate() {
        return progressrate;
    }

    public void setProgressrate(int progressrate) {
        this.progressrate = progressrate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
