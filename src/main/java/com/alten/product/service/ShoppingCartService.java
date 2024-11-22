package com.alten.product.service;

import com.alten.product.data.ShoppingCart;

/**
 * @author lanfouf
 **/
public interface ShoppingCartService {
    ShoppingCart getCartForUser();

    ShoppingCart addProductToCart(Long productId, Integer quantity);

    void removeProductFromCart(Long productId, Integer quantity);
}
