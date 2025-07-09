package com.byui.ibcmarketplace.service.category;

import java.util.List;

import com.byui.ibcmarketplace.dto.CategoryDto;
import com.byui.ibcmarketplace.dto.ProductDto;
import com.byui.ibcmarketplace.model.Category;
import com.byui.ibcmarketplace.request.CategoryRequest;

public interface ICategoryService {
    CategoryDto addCategory(CategoryRequest request);
    CategoryDto updateCategory(CategoryRequest request, Long categoryId);
    void deleteCategory(Long categoryId);
    CategoryDto getCategoryById(Long categoryId);
    CategoryDto getCategoryByName(String name);
    List<CategoryDto> getAllCategories();
    List<ProductDto> getProductsByCategory(Long categoryId);
    Category getCategoryEntityByName(String name);
} 