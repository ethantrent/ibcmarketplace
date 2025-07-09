package com.byui.ibcmarketplace.service.order;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.byui.ibcmarketplace.dto.OrderDto;
import com.byui.ibcmarketplace.dto.OrderItemDto;
import com.byui.ibcmarketplace.dto.UserOrdersDto;
import com.byui.ibcmarketplace.model.Cart;
import com.byui.ibcmarketplace.model.CartItem;
import com.byui.ibcmarketplace.model.Order;
import com.byui.ibcmarketplace.model.OrderItem;
import com.byui.ibcmarketplace.model.OrderStatus;
import com.byui.ibcmarketplace.model.Product;
import com.byui.ibcmarketplace.model.User;
import com.byui.ibcmarketplace.repository.CartItemRepository;
import com.byui.ibcmarketplace.repository.CartRepository;
import com.byui.ibcmarketplace.repository.OrderItemRepository;
import com.byui.ibcmarketplace.repository.OrderRepository;
import com.byui.ibcmarketplace.repository.ProductRepository;
import com.byui.ibcmarketplace.repository.UserRepository;
import com.byui.ibcmarketplace.request.PlaceOrderRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public OrderDto placeOrder(PlaceOrderRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("User not found with id: " + request.getUserId()));
        Cart cart = cartRepository.findByUserId(user.getId())
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Cart not found for user id: " + user.getId()));
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalAmount(java.math.BigDecimal.ZERO);
        for (CartItem cartItem : cart.getItems()) {
            Product product = productRepository.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Product not found with id: " + cartItem.getProduct().getId()));
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice().multiply(java.math.BigDecimal.valueOf(cartItem.getQuantity())));
            order.getOrderItems().add(orderItem);
        }
        order.updateTotalAmount();
        Order savedOrder = orderRepository.save(order);
        cart.getItems().clear();
        cartRepository.save(cart);
        return toDto(savedOrder);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Order not found with id: " + orderId));
        return toDto(order);
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
        return orderRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
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