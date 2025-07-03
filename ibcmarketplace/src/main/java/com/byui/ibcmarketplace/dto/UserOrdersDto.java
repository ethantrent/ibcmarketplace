package com.byui.ibcmarketplace.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserOrdersDto {
    private Long userId;
    private String userName;
    private List<OrderDto> orders;
} 