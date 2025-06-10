package com.byui.ibcmarketplace.request;

import java.math.BigDecimal;

import com.byui.ibcmarketplace.model.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
