package com.byui.ibcmarketplace.service.product;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.byui.ibcmarketplace.dto.ProductDto;
import com.byui.ibcmarketplace.model.Cart;
import com.byui.ibcmarketplace.model.CartItem;
import com.byui.ibcmarketplace.model.Category;
import com.byui.ibcmarketplace.model.Order;
import com.byui.ibcmarketplace.model.OrderItem;
import com.byui.ibcmarketplace.model.Product;
import com.byui.ibcmarketplace.repository.CartItemRepository;
import com.byui.ibcmarketplace.repository.CartRepository;
import com.byui.ibcmarketplace.repository.OrderItemRepository;
import com.byui.ibcmarketplace.repository.OrderRepository;
import com.byui.ibcmarketplace.repository.ProductRepository;
import com.byui.ibcmarketplace.request.AddProductRequest;
import com.byui.ibcmarketplace.request.ProductUpdateRequest;
import com.byui.ibcmarketplace.service.category.CategoryService;

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
    private final ModelMapper modelMapper;

    @Override
    public ProductDto addProducts(AddProductRequest request) {
        if (productExists(request.getName(), request.getBrand())) {
            throw new EntityExistsException(request.getName() + " already exists!");
        }
        
        Category category = categoryService.getCategoryEntityByName(request.getCategory().getName());
        request.setCategory(category);
        Product product = productRepository.save(createProduct(request, category));
        return toDto(product);
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
    public ProductDto updateProduct(ProductUpdateRequest request, Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());
        
        if (request.getCategory() != null) {
            Category category = categoryService.getCategoryEntityByName(request.getCategory().getName());
            existingProduct.setCategory(category);
        }
        
        Product updated = productRepository.save(existingProduct);
        return toDto(updated);
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
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        return toDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand).stream().map(this::toDto).toList();
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category).stream().map(this::toDto).toList();
    }

    @Override
    public List<ProductDto> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name).stream().map(this::toDto).toList();
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand).stream().map(this::toDto).toList();
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        return productRepository.findByName(name).stream().map(this::toDto).toList();
    }

    private ProductDto toDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }
}
