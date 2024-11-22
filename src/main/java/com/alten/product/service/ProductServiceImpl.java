package com.alten.product.service;

import com.alten.product.data.Product;
import com.alten.product.dto.ProductDTO;
import com.alten.product.exception.ResourceNotFoundException;
import com.alten.product.mapper.ProductMapper;
import com.alten.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lanfouf
 **/
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = getProduct(id);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = getProduct(id);
        productMapper.updateEntityFromDto(productDTO, product);
        product.setUpdatedAt(LocalDateTime.now());
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProduct(id);
        productRepository.delete(product);
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }
}
