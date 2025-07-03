package com.byui.ibcmarketplace.service.order;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.byui.ibcmarketplace.dto.OrderDto;
import com.byui.ibcmarketplace.dto.OrderItemDto;
import com.byui.ibcmarketplace.dto.UserOrdersDto;
import com.byui.ibcmarketplace.model.Order;
import com.byui.ibcmarketplace.model.OrderItem;
import com.byui.ibcmarketplace.model.User;
import com.byui.ibcmarketplace.repository.UserRepository;
import com.byui.ibcmarketplace.request.PlaceOrderRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public OrderDto placeOrder(PlaceOrderRequest request) {
        // Implementation to be added
        return null;
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        // Implementation to be added
        return null;
    }

    @Override
    public UserOrdersDto getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("User not found with id: " + userId));
        List<Order> orders = orderRepository.findAll().stream()
            .filter(order -> order.getUser().getId().equals(userId))
            .collect(Collectors.toList());
        UserOrdersDto dto = new UserOrdersDto();
        dto.setUserId(user.getId());
        dto.setUserName(user.getFirstName() + " " + user.getLastName());
        dto.setOrders(orders.stream().map(this::toDto).collect(Collectors.toList()));
        return dto;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        // Implementation to be added
        return null;
    }

    // --- Mapping methods ---
    private OrderDto toDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getOrderId());
        dto.setUserId(order.getUser().getId());
        List<OrderItemDto> items = order.getOrderItems().stream().map(this::toDto).collect(Collectors.toList());
        dto.setItems(items);
        dto.setTotal(order.getTotalAmount());
        dto.setStatus(order.getOrderStatus().toString());
        dto.setCreatedAt(order.getOrderDate().atStartOfDay());
        return dto;
    }

    private OrderItemDto toDto(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getProduct().getPrice());
        dto.setSubtotal(item.getProduct().getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())));
        return dto;
    }
} 