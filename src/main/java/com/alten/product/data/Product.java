package com.alten.product.data;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author lanfouf
 **/
@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private int shellId;

    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;

    private double rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(code, product.code) && Objects.equals(internalReference, product.internalReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, internalReference);
    }
}
