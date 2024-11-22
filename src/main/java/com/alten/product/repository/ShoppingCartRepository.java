package com.alten.product.repository;

import com.alten.product.data.ShoppingCart;
import com.alten.product.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author lanfouf
 **/
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUser(User user);
}
