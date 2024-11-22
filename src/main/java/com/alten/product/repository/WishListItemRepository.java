package com.alten.product.repository;

import com.alten.product.data.Product;
import com.alten.product.data.Wishlist;
import com.alten.product.data.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author lanfouf
 **/
public interface WishListItemRepository extends JpaRepository<WishlistItem, Long> {
    Optional<WishlistItem> findByWishlistAndProduct(Wishlist wishList, Product product);
}
