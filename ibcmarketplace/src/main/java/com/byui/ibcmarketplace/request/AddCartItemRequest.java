package com.byui.ibcmarketplace.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddCartItemRequest {
    @NotNull
    private Long productId;

    @Min(1)
    private int quantity;
} 