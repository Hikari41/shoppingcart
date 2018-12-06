package com.cheer.cart.model;

public class ShoppingCart {
    private Integer cartId;
    private Integer userId;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ShoppingCart{");
        sb.append("cartId=").append(cartId);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
