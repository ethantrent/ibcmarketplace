package com.byui.ibcmarketplace.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
    private Long id;
    
    @NotBlank(message = "Category name is required")
    private String name;
} 