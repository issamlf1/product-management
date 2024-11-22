package com.alten.product.service;

import com.alten.product.data.Wishlist;

/**
 * @author lanfouf
 **/
public interface WishListService {
    Wishlist getWishListForUser();

    Wishlist addProductToWishList(Long productId);

    void removeProductFromWishList(Long productId);
}
