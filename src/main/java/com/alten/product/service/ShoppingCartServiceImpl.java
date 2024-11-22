package com.alten.product.service;

import com.alten.product.data.CartItem;
import com.alten.product.data.Product;
import com.alten.product.data.ShoppingCart;
import com.alten.product.data.User;
import com.alten.product.exception.ResourceNotFoundException;
import com.alten.product.repository.CartItemRepository;
import com.alten.product.repository.ProductRepository;
import com.alten.product.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lanfouf
 **/
@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final AuthService authService;

    @Override
    public ShoppingCart getCartForUser() {
        User user = authService.getCurrentUser();
        return shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found"));
    }

    @Override
    public ShoppingCart addProductToCart(Long productId, Integer quantity) {
        User user = authService.getCurrentUser();
        // Vérifier si le produit existe
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Trouver ou créer un panier pour l'utilisateur
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user).orElseGet(() -> {
            ShoppingCart newCart = new ShoppingCart();
            newCart.setUser(user);
            return shoppingCartRepository.save(newCart);
        });

        // Ajouter un produit dans le panier
        CartItem cartItem = cartItemRepository.findByShoppingCartAndProduct(shoppingCart, product)
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setShoppingCart(shoppingCart);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    return newItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + quantity);  // Ajouter la quantité si déjà présent
        CartItem savedCart = cartItemRepository.save(cartItem);

        // Recalculer le prix total du panier
        shoppingCart.getCartItems().add(savedCart);
        shoppingCart.setTotalPrice(calculateTotal(shoppingCart));
        return shoppingCartRepository.save(shoppingCart);
    }



    @Override
    public void removeProductFromCart(Long productId, Integer quantity) {
        // Récupérer l'utilisateur actuellement connecté
        User user = authService.getCurrentUser();

        // Trouver le produit pour s'assurer qu'il existe
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé"));

        // Trouver le panier de l'utilisateur
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Panier de l'utilisateur non trouvé"));

        // Trouver l'élément du panier correspondant au produit
        CartItem cartItem = cartItemRepository.findByShoppingCartAndProduct(shoppingCart, product)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé dans le panier"));

        // Réduire la quantité ou supprimer complètement l'élément
        if (quantity >= cartItem.getQuantity()) {
            // Si la quantité à supprimer est supérieure ou égale à celle du panier, supprimer l'élément
            cartItemRepository.deleteById(cartItem.getId());
            shoppingCart.getCartItems().remove(cartItem);
        } else {
            // Sinon, réduire la quantité et mettre à jour l'élément du panier
            cartItem.setQuantity(cartItem.getQuantity() - quantity);
            cartItemRepository.save(cartItem);
        }

        // Recalculer le prix total du panier
        shoppingCart.setTotalPrice(calculateTotal(shoppingCart));
        shoppingCartRepository.save(shoppingCart);
    }


    private BigDecimal calculateTotal(ShoppingCart shoppingCart) {
        List<CartItem> items = shoppingCart.getCartItems();

        return items.stream()
                .map(item -> BigDecimal.valueOf(item.getProduct().getPrice()).multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
