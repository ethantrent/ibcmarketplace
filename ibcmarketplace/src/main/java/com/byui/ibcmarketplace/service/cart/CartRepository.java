package com.byui.ibcmarketplace.service.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.byui.ibcmarketplace.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
} 