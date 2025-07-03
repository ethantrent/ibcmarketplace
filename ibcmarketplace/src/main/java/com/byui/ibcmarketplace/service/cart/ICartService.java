package com.byui.ibcmarketplace.service.cart;

import com.byui.ibcmarketplace.dto.CartDto;
import com.byui.ibcmarketplace.request.AddCartItemRequest;
import com.byui.ibcmarketplace.request.UpdateCartItemRequest;

public interface ICartService {
    CartDto getCartByUserId(Long userId);
    CartDto addCartItem(Long userId, AddCartItemRequest request);
    CartDto updateCartItem(Long userId, Long cartItemId, UpdateCartItemRequest request);
    void removeCartItem(Long userId, Long cartItemId);
    void clearCart(Long userId);
} 