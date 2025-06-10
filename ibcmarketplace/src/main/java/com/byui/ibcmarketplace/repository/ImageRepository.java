package com.byui.ibcmarketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byui.ibcmarketplace.model.Image;
import com.byui.ibcmarketplace.model.Product;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProduct(Product product);
} 