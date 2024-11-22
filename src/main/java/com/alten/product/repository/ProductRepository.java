package com.alten.product.repository;

import com.alten.product.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lanfouf
 **/
public interface ProductRepository extends JpaRepository<Product, Long> {
}
