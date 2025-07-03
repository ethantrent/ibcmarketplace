package com.byui.ibcmarketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byui.ibcmarketplace.dto.APIResponse;
import com.byui.ibcmarketplace.dto.OrderDto;
import com.byui.ibcmarketplace.dto.UserOrdersDto;
import com.byui.ibcmarketplace.request.PlaceOrderRequest;
import com.byui.ibcmarketplace.service.order.IOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<APIResponse<OrderDto>> placeOrder(@Valid @RequestBody PlaceOrderRequest request) {
        OrderDto order = orderService.placeOrder(request);
        return ResponseEntity.ok(new APIResponse<>(true, "Order placed successfully", order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<APIResponse<OrderDto>> getOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(new APIResponse<>(true, "Order fetched successfully", order));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<APIResponse<UserOrdersDto>> getOrdersByUser(@PathVariable Long userId) {
        UserOrdersDto userOrders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(new APIResponse<>(true, "Orders fetched successfully", userOrders));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<OrderDto>>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(new APIResponse<>(true, "All orders fetched successfully", orders));
    }
} 