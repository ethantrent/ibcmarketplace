package com.byui.ibcmarketplace.dto;

import lombok.Data;

@Data
public class UserProfileDto {
    private UserDto user;
    private CartDto cart;
    private UserOrdersDto orders;
} 