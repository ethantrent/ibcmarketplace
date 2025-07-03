package com.byui.ibcmarketplace.service.image;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.byui.ibcmarketplace.model.Image;
import com.byui.ibcmarketplace.model.Product;
import com.byui.ibcmarketplace.repository.ImageRepository;
import com.byui.ibcmarketplace.service.product.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    
    private static final List<String> SUPPORTED_IMAGE_TYPES = Arrays.asList(
        "image/jpeg",
        "image/png",
        "image/gif",
        "image/webp"
    );

    @Override
    @Transactional
    public Image uploadImage(MultipartFile file, Long productId) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

            // Validate file
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Cannot upload empty file");
            }

            // Validate file type
            String contentType = file.getContentType();
            if (contentType == null || !SUPPORTED_IMAGE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Unsupported file type. Supported types are: " + SUPPORTED_IMAGE_TYPES);
            }

            // Create blob from file
            byte[] bytes = file.getBytes();
            Blob blob = new SerialBlob(bytes);

            // Create image entity
            Image image = new Image();
            image.setFileName(generateUniqueFileName(file.getOriginalFilename()));
            image.setFileType(contentType);
            image.setImage(blob);
            image.setProduct(product);
            image.setDownloadUrl(generateDownloadUrl(image.getFileName()));

            return imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to upload image: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Image updateImage(Long imageId, MultipartFile file) {
        try {
            // Get existing image
            Image existingImage = getImageById(imageId);

            // Validate file
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Cannot upload empty file");
            }

            // Validate file type
            String contentType = file.getContentType();
            if (contentType == null || !SUPPORTED_IMAGE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Unsupported file type. Supported types are: " + SUPPORTED_IMAGE_TYPES);
            }

            // Create new blob from file
            byte[] bytes = file.getBytes();
            Blob blob = new SerialBlob(bytes);

            // Update image properties
            existingImage.setFileName(generateUniqueFileName(file.getOriginalFilename()));
            existingImage.setFileType(contentType);
            existingImage.setImage(blob);
            existingImage.setDownloadUrl(generateDownloadUrl(existingImage.getFileName()));

            return imageRepository.save(existingImage);
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to update image: " + e.getMessage(), e);
        }
    }

    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image not found with id: " + imageId));
    }

    @Override
    public List<Image> getImagesByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        return imageRepository.findByProduct(product);
    }

    @Override
    @Transactional
    public void deleteImage(Long imageId) {
        Image image = getImageById(imageId);
        imageRepository.delete(image);
    }

    @Override
    public Blob getImageData(Long imageId) {
        Image image = getImageById(imageId);
        return image.getImage();
    }

    @Override
    public String getImageDownloadUrl(Long imageId) {
        Image image = getImageById(imageId);
        return image.getDownloadUrl();
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return UUID.randomUUID().toString() + extension;
    }

    private String generateDownloadUrl(String fileName) {
        // In a real application, this would generate a proper URL
        // For now, we'll just return a placeholder
        return "/api/images/download/" + fileName;
    }
} 