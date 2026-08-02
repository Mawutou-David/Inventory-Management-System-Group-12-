package com.example.myapplication;

import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String name;
    private String code;
    private String description;
    private String image;
    private int status; // 1 for Active, 0 for Inactive
    private String updatedAt;

    public Category(int id, String name, String code, String description, String image, int status, String updatedAt) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.image = image;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public String getImage() { return image; }
    public int getStatus() { return status; }
    public String getUpdatedAt() { return updatedAt; }

    public void setName(String name) { this.name = name; }
    public void setCode(String code) { this.code = code; }
    public void setDescription(String description) { this.description = description; }
    public void setImage(String image) { this.image = image; }
    public void setStatus(int status) { this.status = status; }
}