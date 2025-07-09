package com.byui.ibcmarketplace.service.image;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.sql.rowset.serial.SerialBlob;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.byui.ibcmarketplace.dto.ImageDto;
import com.byui.ibcmarketplace.model.Image;
import com.byui.ibcmarketplace.model.Product;
import com.byui.ibcmarketplace.repository.ImageRepository;
import com.byui.ibcmarketplace.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    
    private static final List<String> SUPPORTED_IMAGE_TYPES = Arrays.asList(
        "image/jpeg",
        "image/png",
        "image/gif",
        "image/webp"
    );

    @Override
    @Transactional
    public ImageDto uploadImage(MultipartFile file, Long productId) {
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

            Image saved = imageRepository.save(image);
            return toDto(saved);
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to upload image: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public ImageDto updateImage(Long imageId, MultipartFile file) {
        try {
            // Get existing image
            Image existingImage = imageRepository.findById(imageId)
                    .orElseThrow(() -> new EntityNotFoundException("Image not found with id: " + imageId));

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

            Image updated = imageRepository.save(existingImage);
            return toDto(updated);
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to update image: " + e.getMessage(), e);
        }
    }

    @Override
    public ImageDto getImageById(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image not found with id: " + imageId));
        return toDto(image);
    }

    @Override
    public List<ImageDto> getImagesByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        return imageRepository.findByProduct(product).stream().map(this::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image not found with id: " + imageId));
        imageRepository.delete(image);
    }

    @Override
    public Blob getImageData(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image not found with id: " + imageId));
        return image.getImage();
    }

    @Override
    public String getImageDownloadUrl(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image not found with id: " + imageId));
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

    private ImageDto toDto(Image image) {
        return modelMapper.map(image, ImageDto.class);
    }
} 