package com.ABCLab.ABCLab.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;

@Document
@Data
@NoArgsConstructor
public class Appointment {

    @Id
    private BigInteger aid;
    private String name;
    private String test;
    private String email;
    private String doctor;
    private String datetime;
    @Field("lastDigit")
    private String lastDigit;

    @Field("paymentId")
    private String paymentId;

    public String getLastDigit() {
        return lastDigit;
    }

    public void setLastDigit(String lastDigit) {
        this.lastDigit = lastDigit;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public BigInteger getAid() {
        return aid;
    }

    public void setAid(BigInteger aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }



}

