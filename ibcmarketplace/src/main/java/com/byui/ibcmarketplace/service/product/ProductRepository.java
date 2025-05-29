package com.byui.ibcmarketplace.service.product;

import com.byui.ibcmarketplace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByCategory(String category);

    List<Product> findByBrandAndName(String brand, String name);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")

    List<Product> findByBrand(String brand);

    List<Product> findByName(String name);

    boolean existsByNameAndBrand(String name, String brand);
}
