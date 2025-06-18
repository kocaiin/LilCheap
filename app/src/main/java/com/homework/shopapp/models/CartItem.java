package com.homework.shopapp.models;

public class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int qty) { this.quantity = qty; }
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return product.getName().equals(cartItem.getProduct().getName());
    }

    @Override
    public int hashCode() {
        return product.getName().hashCode();
    }
}
