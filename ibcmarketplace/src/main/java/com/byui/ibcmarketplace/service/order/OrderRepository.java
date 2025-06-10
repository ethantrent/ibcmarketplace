package com.byui.ibcmarketplace.service.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.byui.ibcmarketplace.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
} 