package com.alten.product.service;

import com.alten.product.dto.ProductDTO;

import java.util.List;

/**
 * @author lanfouf
 **/
public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);
}
