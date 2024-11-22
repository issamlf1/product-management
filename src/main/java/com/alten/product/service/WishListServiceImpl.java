package com.alten.product.service;

import com.alten.product.data.Product;
import com.alten.product.data.User;
import com.alten.product.data.Wishlist;
import com.alten.product.data.WishlistItem;
import com.alten.product.exception.ResourceNotFoundException;
import com.alten.product.repository.ProductRepository;
import com.alten.product.repository.WishListItemRepository;
import com.alten.product.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lanfouf
 **/
@Service
@RequiredArgsConstructor
@Transactional
public class WishListServiceImpl implements WishListService {
    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final AuthService authService;
    private final WishListItemRepository wishListItemRepository;

    @Override
    public Wishlist getWishListForUser() {
        User user = authService.getCurrentUser();
        return wishListRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wish list not found"));
    }

    @Override
    public Wishlist addProductToWishList(Long productId) {
        User user = authService.getCurrentUser();
        // Vérifier si le produit existe
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Trouver ou créer une liste de souhaits pour l'utilisateur
        Wishlist wishList = wishListRepository.findByUser(user).orElseGet(() -> {
            Wishlist newWishList = new Wishlist();
            newWishList.setUser(user);
            return wishListRepository.save(newWishList);
        });

        // Ajouter un produit à la liste de souhaits
        WishlistItem wishListItem = wishListItemRepository.findByWishlistAndProduct(wishList, product)
                .orElseGet(() -> {
                    WishlistItem newItem = new WishlistItem();
                    newItem.setWishlist(wishList);
                    newItem.setProduct(product);
                    return newItem;
                });

        // Sauvegarder l'élément de la liste de souhaits
        WishlistItem savedListItem = wishListItemRepository.save(wishListItem);
        wishList.getWishlistItems().add(savedListItem);

        // Retourner la liste de souhaits avec les nouveaux produits
        return wishListRepository.save(wishList);
    }

    @Override
    public void removeProductFromWishList(Long productId) {
        User user = authService.getCurrentUser();
        // Trouver le produit pour s'assurer qu'il existe
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Trouver la liste de souhaits de l'utilisateur
        Wishlist wishList = wishListRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wish list not found"));

        // Trouver l'élément de la liste de souhaits correspondant au produit
        WishlistItem wishListItem = wishListItemRepository.findByWishlistAndProduct(wishList, product)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in wish list"));

        // Supprimer l'élément de la liste de souhaits
        wishListItemRepository.delete(wishListItem);

        // Sauvegarder les modifications dans la liste de souhaits
        wishListRepository.save(wishList);
    }


}
