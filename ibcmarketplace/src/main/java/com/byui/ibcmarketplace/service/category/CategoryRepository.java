package com.byui.ibcmarketplace.service.category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.byui.ibcmarketplace.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    boolean existsByName(String name);
} 