package com.byui.ibcmarketplace.dto;

public record APIResponse<T>(
    T data,
    String message
) {} 