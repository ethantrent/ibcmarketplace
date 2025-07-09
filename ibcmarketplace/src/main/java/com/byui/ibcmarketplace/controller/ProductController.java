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
import com.byui.ibcmarketplace.dto.ProductDto;
import com.byui.ibcmarketplace.request.AddProductRequest;
import com.byui.ibcmarketplace.request.ProductUpdateRequest;
import com.byui.ibcmarketplace.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PostMapping
    public ResponseEntity<APIResponse<ProductDto>> addProduct(@RequestBody AddProductRequest request) {
        ProductDto product = productService.addProducts(request);
        return ResponseEntity.ok(new APIResponse<>(product, "Product created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ProductDto>> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        ProductDto product = productService.updateProduct(request, id);
        return ResponseEntity.ok(new APIResponse<>(product, "Product updated successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ProductDto>> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(new APIResponse<>(product, "Product fetched successfully"));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<ProductDto>>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(new APIResponse<>(products, "Products fetched successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProductId(id);
        return ResponseEntity.ok(new APIResponse<Void>(null, "Product deleted successfully"));
    }
} 