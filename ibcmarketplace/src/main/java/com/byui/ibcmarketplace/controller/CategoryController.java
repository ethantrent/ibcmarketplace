package com.byui.ibcmarketplace.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byui.ibcmarketplace.dto.APIResponse;
import com.byui.ibcmarketplace.dto.CategoryDto;
import com.byui.ibcmarketplace.model.Category;
import com.byui.ibcmarketplace.model.Product;
import com.byui.ibcmarketplace.request.CategoryRequest;
import com.byui.ibcmarketplace.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;
    
    @Autowired
    private ModelMapper modelMapper;

    private CategoryDto toDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    @PostMapping
    public ResponseEntity<APIResponse<CategoryDto>> addCategory(@RequestBody CategoryRequest request) {
        Category category = categoryService.addCategory(request);
        return ResponseEntity.ok(new APIResponse<>(true, "Category created successfully", toDto(category)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryDto>> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request) {
        Category category = categoryService.updateCategory(request, id);
        return ResponseEntity.ok(new APIResponse<>(true, "Category updated successfully", toDto(category)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryDto>> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Category fetched successfully", toDto(category)));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<APIResponse<CategoryDto>> getCategoryByName(@PathVariable String name) {
        Category category = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(new APIResponse<>(true, "Category fetched successfully", toDto(category)));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<CategoryDto>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDto> dtos = categories.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(new APIResponse<>(true, "Categories fetched successfully", dtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Category deleted successfully", null));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getProductsByCategory(id));
    }
} 