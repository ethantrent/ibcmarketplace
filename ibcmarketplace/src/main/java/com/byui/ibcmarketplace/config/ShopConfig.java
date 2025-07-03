package com.byui.ibcmarketplace.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.byui.ibcmarketplace.dto.ProductDto;
import com.byui.ibcmarketplace.dto.ImageDto;
import com.byui.ibcmarketplace.model.Product;
import com.byui.ibcmarketplace.model.Image;

@Configuration
public class ShopConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        // Configure ModelMapper for strict matching
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(true);
        
        // Custom mapping for Product to ProductDto
        modelMapper.createTypeMap(Product.class, ProductDto.class)
                .addMappings(mapper -> {
                    mapper.map(Product::getId, ProductDto::setId);
                    mapper.map(Product::getName, ProductDto::setName);
                    mapper.map(Product::getBrand, ProductDto::setBrand);
                    mapper.map(Product::getPrice, ProductDto::setPrice);
                    mapper.map(Product::getInventory, ProductDto::setInventory);
                    mapper.map(Product::getDescription, ProductDto::setDescription);
                    mapper.map(src -> src.getCategory() != null ? src.getCategory().getName() : null, 
                             ProductDto::setCategory);
                });
        
        // Custom mapping for Image to ImageDto
        modelMapper.createTypeMap(Image.class, ImageDto.class)
                .addMappings(mapper -> {
                    mapper.map(Image::getId, ImageDto::setId);
                    mapper.map(Image::getFileName, ImageDto::setFileName);
                    mapper.map(Image::getFileType, ImageDto::setFileType);
                    mapper.map(Image::getDownloadUrl, ImageDto::setDownloadUrl);
                    mapper.map(src -> src.getProduct() != null ? src.getProduct().getId() : null, 
                             ImageDto::setProductId);
                });
        
        return modelMapper;
    }
} 