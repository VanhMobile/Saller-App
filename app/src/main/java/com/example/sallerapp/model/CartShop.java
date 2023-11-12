package com.example.sallerapp.model;

import java.util.ArrayList;

public class CartShop {
    private Product product;
    private int quantity;

    public CartShop(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CartShop cartShop = (CartShop) obj;
        return this .getProduct().getProductId().equals(cartShop.getProduct().getProductId());
    }
    @Override
    public int hashCode() {
        int result = this.getProduct().getProductId().hashCode();
        return result;
    }
}
