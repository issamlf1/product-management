package com.alten.product.controller;

import com.alten.product.data.Wishlist;
import com.alten.product.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author lanfouf
 **/
@RestController
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class WishListController {
    private final WishListService wishListService;

    @Operation(
            summary = "Retrieve wishlist",
            description = "Fetch all the products in the authenticated user's wishlist."
    )
    @GetMapping
    public ResponseEntity<Wishlist> getWishList() {
        Wishlist wishList = wishListService.getWishListForUser();
        return ResponseEntity.ok(wishList);
    }

    @Operation(
            summary = "Add product to wishlist",
            description = "Allows the authenticated user to add a product to their wishlist."
    )
    @PostMapping("/{productId}")
    public ResponseEntity<Wishlist> addProductToWishList(@PathVariable Long productId) {
        Wishlist wishList = wishListService.addProductToWishList(productId);
        return ResponseEntity.ok(wishList);
    }

    @Operation(
            summary = "Remove product from wishlist",
            description = "Allows the authenticated user to remove a product from their wishlist."
    )
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProductFromWishList(@PathVariable Long productId) {
        wishListService.removeProductFromWishList(productId);
        return ResponseEntity.noContent().build();
    }
}
