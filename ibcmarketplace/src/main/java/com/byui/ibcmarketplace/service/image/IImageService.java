package com.byui.ibcmarketplace.service.image;

import java.sql.Blob;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.byui.ibcmarketplace.dto.ImageDto;

public interface IImageService {
    ImageDto uploadImage(MultipartFile file, Long productId);
    ImageDto updateImage(Long imageId, MultipartFile file);
    ImageDto getImageById(Long imageId);
    List<ImageDto> getImagesByProduct(Long productId);
    void deleteImage(Long imageId);
    Blob getImageData(Long imageId);
    String getImageDownloadUrl(Long imageId);
} 