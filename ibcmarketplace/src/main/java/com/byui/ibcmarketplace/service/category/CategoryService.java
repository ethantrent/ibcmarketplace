package com.byui.ibcmarketplace.service.category;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.byui.ibcmarketplace.model.Category;
import com.byui.ibcmarketplace.model.Product;
import com.byui.ibcmarketplace.request.CategoryRequest;
import com.byui.ibcmarketplace.service.product.ProductRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Category addCategory(@Valid CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new EntityExistsException("Category with name '" + request.getName() + "' already exists");
        }
        Category category = new Category(request.getName());
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(@Valid CategoryRequest request, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

        if (!existingCategory.getName().equals(request.getName()) && 
            categoryRepository.existsByName(request.getName())) {
            throw new EntityExistsException("Category with name '" + request.getName() + "' already exists");
        }

        List<Product> products = productRepository.findByCategoryName(existingCategory.getName());
        products.forEach(product -> product.setCategory(existingCategory));

        existingCategory.setName(request.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

        List<Product> products = productRepository.findByCategoryName(category.getName());
        if (!products.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Cannot delete category that has products. Product IDs: ");
            products.forEach(product -> errorMessage.append(product.getId()).append(", "));
            throw new IllegalStateException(errorMessage.toString().replaceAll(", $", ""));
        }

        categoryRepository.delete(category);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
    }

    @Override
    public Category getCategoryByName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with name: " + name));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);
        return productRepository.findByCategoryName(category.getName());
    }
} 