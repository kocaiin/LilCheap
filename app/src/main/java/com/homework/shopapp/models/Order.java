package com.homework.shopapp.models;

import java.util.List;

public class Order {
    private String orderId;
    private List<CartItem> items;
    private String status; // e.g., "Pending", "Delivered"
    private long timestamp;

    public Order(String orderId, List<CartItem> items, String status, long timestamp) {
        this.orderId = orderId;
        this.items = items;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
