package com.byui.ibcmarketplace.service.order;

import java.util.List;

import com.byui.ibcmarketplace.dto.OrderDto;
import com.byui.ibcmarketplace.dto.UserOrdersDto;
import com.byui.ibcmarketplace.request.PlaceOrderRequest;

public interface IOrderService {
    OrderDto placeOrder(PlaceOrderRequest request);
    OrderDto getOrderById(Long orderId);
    UserOrdersDto getOrdersByUserId(Long userId);
    List<OrderDto> getAllOrders();
} 