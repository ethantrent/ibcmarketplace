package com.byui.ibcmarketplace.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private String category;
    private List<ImageDto> images;
}