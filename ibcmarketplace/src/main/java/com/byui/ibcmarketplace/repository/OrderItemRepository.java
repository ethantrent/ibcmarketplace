package com.byui.ibcmarketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byui.ibcmarketplace.model.OrderItem;
import com.byui.ibcmarketplace.model.Product;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByProduct(Product product);
    List<OrderItem> findByProductId(Long productId);
    void deleteByProductId(Long productId);
} 