package com.byui.ibcmarketplace.service.category;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.byui.ibcmarketplace.dto.CategoryDto;
import com.byui.ibcmarketplace.dto.ProductDto;
import com.byui.ibcmarketplace.model.Category;
import com.byui.ibcmarketplace.model.Product;
import com.byui.ibcmarketplace.repository.CategoryRepository;
import com.byui.ibcmarketplace.repository.ProductRepository;
import com.byui.ibcmarketplace.request.CategoryRequest;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public CategoryDto addCategory(@Valid CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new EntityExistsException("Category with name '" + request.getName() + "' already exists");
        }
        Category category = new Category(request.getName());
        Category saved = categoryRepository.save(category);
        return toDto(saved);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(@Valid CategoryRequest request, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

        if (!existingCategory.getName().equals(request.getName()) && 
            categoryRepository.existsByName(request.getName())) {
            throw new EntityExistsException("Category with name '" + request.getName() + "' already exists");
        }

        List<Product> products = productRepository.findByCategoryName(existingCategory.getName());
        products.forEach(product -> product.setCategory(existingCategory));

        existingCategory.setName(request.getName());
        Category updated = categoryRepository.save(existingCategory);
        return toDto(updated);
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
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        return toDto(category);
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with name: " + name));
        return toDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        return productRepository.findByCategoryName(category.getName()).stream().map(this::toProductDto).toList();
    }

    @Override
    public Category getCategoryEntityByName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with name: " + name));
    }

    private CategoryDto toDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }
    private ProductDto toProductDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }
} 