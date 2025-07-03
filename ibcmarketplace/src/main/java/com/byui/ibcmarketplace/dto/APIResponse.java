package com.byui.ibcmarketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponse<T> {
    private boolean success;
    private String message;
    private T data;
} 