package com.example.myapplication;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
    private String category;
    private String supplier;
    private double price;
    private int quantity;

    public Product(String id, String name, String category, String supplier, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.supplier = supplier;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getSupplier() { return supplier; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}