package com.ikenna.portfolios.infos;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Doc {
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private Integer id;
        private String docName;
        private String docType;
        @Lob
        private byte[] data;
        private String urlDownload;
        private String link;
        private String projectTitle;
        private String keyRole;
        private String projectSummary;
        private String progress;
        private Date startDate;
        private Date endDate;

        public Doc() {}

    public Doc(
               String docName,
               String docType,
               byte[] data,
              String urlDownload,
              String link,
              String projectTitle,
               String projectSummary,
              String progress,
               String keyRole,
               Date startDate,
               Date endDate ) {

        this.docName = docName;
        this.docType = docType;
        this.data = data;
        this.urlDownload = urlDownload;
        this.link = link;
        this.projectTitle = projectTitle;
        this.projectSummary = projectSummary;
        this.progress = progress;
        this.keyRole = keyRole;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public String getUrlDownload() {
        return urlDownload;
    }

    public String setUrlDownload(String urlDownload) {
        this.urlDownload = urlDownload;
        return urlDownload;
    }

    public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDocName() {
            return docName;
        }

        public void setDocName(String docName) {
            this.docName = docName;
        }

        public String getDocType() {
            return docType;
        }

        public void setDocType(String docType) {
            this.docType = docType;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
