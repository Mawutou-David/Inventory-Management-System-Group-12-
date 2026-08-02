package com.example.myapplication;

import java.io.Serializable;

public class Customer implements Serializable {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String company;
    private String address;
    private String type; // Retail / Wholesale
    private String tin; // Tax Identification Number
    private String notes;
    private double balance;
    private int status; // 1 for Active, 0 for Inactive
    private String registrationDate;
    private String lastPurchaseDate;
    private int totalPurchases;

    public Customer(String id, String name, String phone, String email, String company, String address, String type, String tin, String notes, double balance, int status, String registrationDate, String lastPurchaseDate, int totalPurchases) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.address = address;
        this.type = type;
        this.tin = tin;
        this.notes = notes;
        this.balance = balance;
        this.status = status;
        this.registrationDate = registrationDate;
        this.lastPurchaseDate = lastPurchaseDate;
        this.totalPurchases = totalPurchases;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getCompany() { return company; }
    public String getAddress() { return address; }
    public String getType() { return type; }
    public String getTin() { return tin; }
    public String getNotes() { return notes; }
    public double getBalance() { return balance; }
    public int getStatus() { return status; }
    public String getRegistrationDate() { return registrationDate; }
    public String getLastPurchaseDate() { return lastPurchaseDate; }
    public int getTotalPurchases() { return totalPurchases; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setCompany(String company) { this.company = company; }
    public void setAddress(String address) { this.address = address; }
    public void setType(String type) { this.type = type; }
    public void setTin(String tin) { this.tin = tin; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setBalance(double balance) { this.balance = balance; }
    public void setStatus(int status) { this.status = status; }
}