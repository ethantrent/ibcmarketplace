package com.byui.ibcmarketplace.service.category;

import java.util.List;

import com.byui.ibcmarketplace.model.Category;
import com.byui.ibcmarketplace.model.Product;
import com.byui.ibcmarketplace.request.CategoryRequest;

public interface ICategoryService {
    Category addCategory(CategoryRequest request);
    Category updateCategory(CategoryRequest request, Long categoryId);
    void deleteCategory(Long categoryId);
    Category getCategoryById(Long categoryId);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    List<Product> getProductsByCategory(Long categoryId);
} 