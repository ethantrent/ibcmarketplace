package com.byui.ibcmarketplace.controller;

import java.util.List;

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
import com.byui.ibcmarketplace.dto.ProductDto;
import com.byui.ibcmarketplace.request.CategoryRequest;
import com.byui.ibcmarketplace.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<APIResponse<CategoryDto>> addCategory(@RequestBody CategoryRequest request) {
        CategoryDto category = categoryService.addCategory(request);
        return ResponseEntity.ok(new APIResponse<>(category, "Category created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryDto>> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request) {
        CategoryDto category = categoryService.updateCategory(request, id);
        return ResponseEntity.ok(new APIResponse<>(category, "Category updated successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryDto>> getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new APIResponse<>(category, "Category fetched successfully"));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<APIResponse<CategoryDto>> getCategoryByName(@PathVariable String name) {
        CategoryDto category = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(new APIResponse<>(category, "Category fetched successfully"));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<CategoryDto>>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new APIResponse<>(categories, "Categories fetched successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new APIResponse<>(null, "Category deleted successfully"));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<APIResponse<List<ProductDto>>> getProductsByCategory(@PathVariable Long id) {
        List<ProductDto> products = categoryService.getProductsByCategory(id);
        return ResponseEntity.ok(new APIResponse<>(products, "Products by category fetched successfully"));
    }
} 