package com.byui.ibcmarketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byui.ibcmarketplace.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
} 