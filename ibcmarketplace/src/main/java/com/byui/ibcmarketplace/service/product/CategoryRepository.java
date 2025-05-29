package com.byui.ibcmarketplace.service.product;

import com.byui.ibcmarketplace.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
