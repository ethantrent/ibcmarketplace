package com.byui.ibcmarketplace.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private List<OrderItemDto> items;
    private BigDecimal total;
    private String status;
    private LocalDateTime createdAt;
} 