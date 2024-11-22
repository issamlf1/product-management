package com.alten.product.controller;

import com.alten.product.dto.ProductDTO;
import com.alten.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author lanfouf
 **/
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Create a new product", description = "Create a new product")
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO, UriComponentsBuilder ucb) {
        URI locationOfProduct = ucb.path("/products/{id}").buildAndExpand(productService.createProduct(productDTO).getId()).toUri();
        return ResponseEntity.created(locationOfProduct).body(productService.createProduct(productDTO));
    }

    @Operation(summary = "Retrieve all products",
            description = "This endpoint fetches all the products")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Get a product by ID", description = "Retrieve a product by its unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Update a product", description = "Update some product details based on its ID")
    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @Operation(summary = "Delete a product", description = "Delete a product by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
