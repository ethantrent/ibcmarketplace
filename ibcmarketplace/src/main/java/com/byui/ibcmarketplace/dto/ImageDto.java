package com.byui.ibcmarketplace.dto;

import lombok.Data;

@Data
public class ImageDto {
    private Long id;
    private String fileName;
    private String fileType;
    private String downloadUrl;
    private Long productId;
}