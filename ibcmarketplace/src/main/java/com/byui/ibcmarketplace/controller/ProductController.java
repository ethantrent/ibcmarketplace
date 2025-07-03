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
import com.byui.ibcmarketplace.dto.ProductDto;
import com.byui.ibcmarketplace.model.Product;
import com.byui.ibcmarketplace.request.AddProductRequest;
import com.byui.ibcmarketplace.request.ProductUpdateRequest;
import com.byui.ibcmarketplace.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    
    @Autowired
    private ModelMapper modelMapper;

    private ProductDto toDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    @PostMapping
    public ResponseEntity<APIResponse<ProductDto>> addProduct(@RequestBody AddProductRequest request) {
        Product product = productService.addProducts(request);
        return ResponseEntity.ok(new APIResponse<>(true, "Product created successfully", toDto(product)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ProductDto>> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        Product product = productService.updateProduct(request, id);
        return ResponseEntity.ok(new APIResponse<>(true, "Product updated successfully", toDto(product)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ProductDto>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Product fetched successfully", toDto(product)));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<ProductDto>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> dtos = products.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(new APIResponse<>(true, "Products fetched successfully", dtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProductId(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Product deleted successfully", null));
    }
} 