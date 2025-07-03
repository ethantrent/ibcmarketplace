package com.byui.ibcmarketplace.service.image;

import java.sql.Blob;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.byui.ibcmarketplace.model.Image;

public interface IImageService {
    Image uploadImage(MultipartFile file, Long productId);
    Image updateImage(Long imageId, MultipartFile file);
    Image getImageById(Long imageId);
    List<Image> getImagesByProduct(Long productId);
    void deleteImage(Long imageId);
    Blob getImageData(Long imageId);
    String getImageDownloadUrl(Long imageId);
} 