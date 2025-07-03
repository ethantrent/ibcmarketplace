package com.byui.ibcmarketplace.service.cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.byui.ibcmarketplace.dto.CartDto;
import com.byui.ibcmarketplace.dto.CartItemDto;
import com.byui.ibcmarketplace.model.Cart;
import com.byui.ibcmarketplace.model.CartItem;
import com.byui.ibcmarketplace.model.Product;
import com.byui.ibcmarketplace.model.User;
import com.byui.ibcmarketplace.repository.UserRepository;
import com.byui.ibcmarketplace.request.AddCartItemRequest;
import com.byui.ibcmarketplace.request.UpdateCartItemRequest;
import com.byui.ibcmarketplace.service.product.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public CartDto getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException("Cart not found for user id: " + userId));
        return toDto(cart);
    }

    @Override
    public CartDto addCartItem(Long userId, AddCartItemRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + request.getProductId()));
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }
        // Check if item already exists
        CartItem item = cart.getItems().stream()
            .filter(ci -> ci.getProduct().getId().equals(product.getId()))
            .findFirst().orElse(null);
        if (item != null) {
            item.setQuantity(item.getQuantity() + request.getQuantity());
            cartItemRepository.save(item);
        } else {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(request.getQuantity());
            cartItemRepository.save(item);
            cart.getItems().add(item);
        }
        cart = cartRepository.save(cart);
        return toDto(cart);
    }

    @Override
    public CartDto updateCartItem(Long userId, Long cartItemId, UpdateCartItemRequest request) {
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException("Cart not found for user id: " + userId));
        CartItem item = cart.getItems().stream()
            .filter(ci -> ci.getId().equals(cartItemId))
            .findFirst().orElseThrow(() -> new EntityNotFoundException("Cart item not found with id: " + cartItemId));
        item.setQuantity(request.getQuantity());
        cartItemRepository.save(item);
        cart = cartRepository.save(cart);
        return toDto(cart);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) {
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException("Cart not found for user id: " + userId));
        CartItem item = cart.getItems().stream()
            .filter(ci -> ci.getId().equals(cartItemId))
            .findFirst().orElseThrow(() -> new EntityNotFoundException("Cart item not found with id: " + cartItemId));
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException("Cart not found for user id: " + userId));
        for (CartItem item : cart.getItems()) {
            cartItemRepository.delete(item);
        }
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    // --- Mapping methods ---
    private CartDto toDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        List<CartItemDto> items = cart.getItems().stream().map(this::toDto).collect(Collectors.toList());
        dto.setItems(items);
        dto.setTotal(items.stream().map(CartItemDto::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add));
        return dto;
    }

    private CartItemDto toDto(CartItem item) {
        CartItemDto dto = new CartItemDto();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getProduct().getPrice());
        dto.setSubtotal(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        return dto;
    }
} 