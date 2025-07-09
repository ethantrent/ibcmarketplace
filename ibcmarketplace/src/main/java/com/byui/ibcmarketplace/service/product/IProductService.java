package com.byui.ibcmarketplace.service.product;

import java.util.List;

import com.byui.ibcmarketplace.dto.ProductDto;
import com.byui.ibcmarketplace.request.AddProductRequest;
import com.byui.ibcmarketplace.request.ProductUpdateRequest;

public interface IProductService {
    ProductDto addProducts(AddProductRequest product);
    ProductDto updateProduct(ProductUpdateRequest product, Long productId);
    ProductDto getProductById(Long productId);
    void deleteProductId(Long productId);

    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByCategoryAndBrand(String category, String brand);
    List<ProductDto> getProductsByCategory(String category);
    List<ProductDto> getProductsByBrandAndName(String brand, String name);
    List<ProductDto> getProductsByBrand(String brand);
    List<ProductDto> getProductsByName(String name);
}
