package com.alten.product.dto;

import lombok.*;

/**
 * @author lanfouf
 **/
@Data
@Builder
public class ProductDTO {
    private Long id;
    private String code;
    private String name;
    private String image;
    private String description;
    private String category;
    private double price;
    private int quantity;
    private double rating;
    private String internalReference;
    private String inventoryStatus;
}
