package com.byui.ibcmarketplace.service.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.byui.ibcmarketplace.model.Cart;
import com.byui.ibcmarketplace.model.CartItem;
import com.byui.ibcmarketplace.model.Category;
import com.byui.ibcmarketplace.model.Order;
import com.byui.ibcmarketplace.model.OrderItem;
import com.byui.ibcmarketplace.model.Product;
import com.byui.ibcmarketplace.request.AddProductRequest;
import com.byui.ibcmarketplace.request.ProductUpdateRequest;
import com.byui.ibcmarketplace.service.cart.CartItemRepository;
import com.byui.ibcmarketplace.service.cart.CartRepository;
import com.byui.ibcmarketplace.service.category.CategoryService;
import com.byui.ibcmarketplace.service.order.OrderItemRepository;
import com.byui.ibcmarketplace.service.order.OrderRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    @Override
    public Product addProducts(AddProductRequest request) {
        if (productExists(request.getName(), request.getBrand())) {
            throw new EntityExistsException(request.getName() + " already exists!");
        }
        
        Category category = categoryService.getCategoryByName(request.getCategory().getName());
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private boolean productExists(String name, String brand) {
        return productRepository.existsByNameAndBrand(name, brand);
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category);
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());
        
        if (request.getCategory() != null) {
            Category category = categoryService.getCategoryByName(request.getCategory().getName());
            existingProduct.setCategory(category);
        }
        
        return productRepository.save(existingProduct);
    }

    @Override
    @Transactional
    public void deleteProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        // Remove product from any orders
        List<OrderItem> orderItems = orderItemRepository.findByProduct(product);
        orderItems.forEach(item -> {
            Order order = item.getOrder();
            order.removeItem(item);
            // Save the updated order
            orderRepository.save(order);
        });

        // Remove product from any active carts
        List<CartItem> cartItems = cartItemRepository.findByProduct(product);
        cartItems.forEach(item -> {
            Cart cart = item.getCart();
            cart.removeItem(item);
            // Save the updated cart
            cartRepository.save(cart);
        });

        // Perform the delete
        productRepository.delete(product);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }
}
