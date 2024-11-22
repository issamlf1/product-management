package com.alten.product.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * @author lanfouf
 **/
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;  // Quantité du produit dans le panier

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    @JsonBackReference
    private ShoppingCart shoppingCart;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id) && Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product);
    }
}
