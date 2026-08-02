package com.example.myapplication;

public class Purchase {
    private int id;
    private String productName;
    private int quantity;
    private String date;
    private double total;

    public Purchase(int id, String productName, int quantity, String date, double total) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.date = date;
        this.total = total;
    }

    public int getId() { return id; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public String getDate() { return date; }
    public double getTotal() { return total; }
}