package com.alten.product.controller;

import com.alten.product.data.ShoppingCart;
import com.alten.product.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author lanfouf
 **/
@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(
            summary = "Retrieve shopping cart",
            description = "Fetch all the products in the authenticated user's shopping cart along with the total price."
    )
    @GetMapping
    public ResponseEntity<ShoppingCart> getCart() {
        ShoppingCart cart = shoppingCartService.getCartForUser();
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Add a product to shopping cart", description = "Allows the authenticated user to add a product to their shopping cart or update its quantity.")
    @PostMapping("/{productId}")
    public ResponseEntity<ShoppingCart> addProductToCart(@PathVariable Long productId,
                                                         @RequestParam Integer quantity) {
        ShoppingCart cart = shoppingCartService.addProductToCart(productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @Operation(
            summary = "Remove product from shopping cart",
            description = "Allows the authenticated user to remove a product from their shopping cart."
    )
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long productId,
                                                      @RequestParam Integer quantity) {
        shoppingCartService.removeProductFromCart(productId, quantity);
        return ResponseEntity.noContent().build();
    }


}
