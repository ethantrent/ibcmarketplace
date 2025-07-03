package com.byui.ibcmarketplace.controller;

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
import com.byui.ibcmarketplace.dto.CartDto;
import com.byui.ibcmarketplace.request.AddCartItemRequest;
import com.byui.ibcmarketplace.request.UpdateCartItemRequest;
import com.byui.ibcmarketplace.service.cart.ICartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse<CartDto>> getCart(@PathVariable Long userId) {
        CartDto cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(new APIResponse<>(true, "Cart fetched successfully", cart));
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<APIResponse<CartDto>> addCartItem(@PathVariable Long userId, @Valid @RequestBody AddCartItemRequest request) {
        CartDto cart = cartService.addCartItem(userId, request);
        return ResponseEntity.ok(new APIResponse<>(true, "Item added to cart", cart));
    }

    @PutMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<APIResponse<CartDto>> updateCartItem(@PathVariable Long userId, @PathVariable Long cartItemId, @Valid @RequestBody UpdateCartItemRequest request) {
        CartDto cart = cartService.updateCartItem(userId, cartItemId, request);
        return ResponseEntity.ok(new APIResponse<>(true, "Cart item updated", cart));
    }

    @DeleteMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<APIResponse<Void>> removeCartItem(@PathVariable Long userId, @PathVariable Long cartItemId) {
        cartService.removeCartItem(userId, cartItemId);
        return ResponseEntity.ok(new APIResponse<>(true, "Cart item removed", null));
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<APIResponse<Void>> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(new APIResponse<>(true, "Cart cleared", null));
    }
} 