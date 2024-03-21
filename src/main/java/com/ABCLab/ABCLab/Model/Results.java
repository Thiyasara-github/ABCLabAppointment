package com.ABCLab.ABCLab.Model;

import org.springframework.data.annotation.Id;

public class Results {
    @Id
    private String name;
    private String email;
    private String test;
    private String doctor;
    private String recommendations;
    private byte[] pdfFileData; // Variable to hold PDF file data

    // Getter and setter methods for pdfFileData
    public byte[] getPdfFileData() {
        return pdfFileData;
    }

    public void setPdfFileData(byte[] pdfFileData) {
        this.pdfFileData = pdfFileData;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}
