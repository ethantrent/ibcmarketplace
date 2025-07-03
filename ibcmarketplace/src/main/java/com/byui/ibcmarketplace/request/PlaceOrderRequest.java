package com.byui.ibcmarketplace.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlaceOrderRequest {
    @NotNull
    private Long userId;
    private String shippingAddress;
} 