package com.alten.product.repository;

import com.alten.product.data.User;
import com.alten.product.data.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author lanfouf
 **/
public interface WishListRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByUser(User user);
}
