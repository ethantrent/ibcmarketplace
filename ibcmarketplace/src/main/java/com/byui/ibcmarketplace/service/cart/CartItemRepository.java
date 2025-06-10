package com.byui.ibcmarketplace.service.cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byui.ibcmarketplace.model.CartItem;
import com.byui.ibcmarketplace.model.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByProduct(Product product);
    List<CartItem> findByProductId(Long productId);
    void deleteByProductId(Long productId);
} 