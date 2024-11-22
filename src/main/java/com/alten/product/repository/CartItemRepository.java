package com.alten.product.repository;

import com.alten.product.data.CartItem;
import com.alten.product.data.Product;
import com.alten.product.data.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author lanfouf
 **/
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByShoppingCartAndProduct(ShoppingCart shoppingCart, Product product);
}
