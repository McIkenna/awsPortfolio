package com.ikenna.portfolios.infos;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Education{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "must include school")
    private String schoolName;
    @NotBlank(message = "must include major")
    private String major;
    @NotBlank(message = "must include concentration")
    private String concentration;
    @NotBlank(message = "must include honors")
    private String honor;
    @Lob
    private byte[] image;
    private String docName;
    private String docType;
    private String urlDownload;
/*
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name="education_id", referencedColumnName = "id")
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    */

    public Education(
                     String schoolName,
                   String major,
                 String concentration,
                    String honor,
                     byte[] image,
                     String docName,
                     String docType,
                     String urlDownload) {
        this.schoolName = schoolName;
        this.major = major;
        this.concentration = concentration;
        this.honor = honor;
        this.image = image;
        this.docName = docName;
        this.docType = docType;
        this.urlDownload = urlDownload;
    }

    public Education() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }


    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public String getUrlDownload() {
        return urlDownload;
    }

    public String setUrlDownload(String urlDownload) {
        this.urlDownload = urlDownload;
        return urlDownload;
    }
}
