package com.homework.shopapp.models;

public class Product {
    private final String name;
    private final String description;
    private final double price;
    private final int imageResId;
    private final String fullDescription; // ✅ declared here


    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    // ✅ Constructor for home page items only
    public Product(String name, String description, double price, int imageResId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
        this.fullDescription = ""; // ✅ default empty
    }

    // ✅ Constructor for detailed product info
    public Product(String name, String description, double price, int imageResId, String fullDescription) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
        this.fullDescription = fullDescription;
    }

    // ✅ Getters
    public String getName() { return name; }
    public String getDescription() { return description; } // short version
    public double getPrice() { return price; }
    public int getImageResId() { return imageResId; }
    public String getFullDescription() { return fullDescription; }
}
