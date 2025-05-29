package com.byui.ibcmarketplace.service.product;

import java.util.List;

import com.byui.ibcmarketplace.model.Product;
import com.byui.ibcmarketplace.request.AddProductRequest;

public interface IProductService {
    Product addProducts(AddProductRequest product);
    Product updateProduct(Product product, Long productId);
    Product getProductById(Long productId);
    void deleteProductId(Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrandAndName(String brand, String name);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByName(String name);
}
