package com.cheer.cart.dao;

import com.cheer.cart.model.ShoppingCart;

public interface ShoppingCartMapper {
    //  展示购物车信息
    ShoppingCart getCart(String cartId);

    //  添加购物车
    void add(ShoppingCart shoppingCart);
}
