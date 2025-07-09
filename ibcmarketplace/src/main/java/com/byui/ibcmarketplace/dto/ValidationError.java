package com.byui.ibcmarketplace.dto;

public record ValidationError(
    String field,
    String message
) {} 