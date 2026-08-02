package com.example.myapplication;

import java.io.Serializable;

public class Customer implements Serializable {
    private String id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String registrationDate;

    public Customer(String id, String fullName, String phoneNumber, String email, String registrationDate) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getRegistrationDate() { return registrationDate; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setRegistrationDate(String registrationDate) { this.registrationDate = registrationDate; }
}
