package com.byui.ibcmarketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byui.ibcmarketplace.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
} 