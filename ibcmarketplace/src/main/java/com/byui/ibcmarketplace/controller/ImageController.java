package com.byui.ibcmarketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.byui.ibcmarketplace.dto.APIResponse;
import com.byui.ibcmarketplace.dto.ImageDto;
import com.byui.ibcmarketplace.service.image.IImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload/{productId}")
    public ResponseEntity<APIResponse<ImageDto>> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable Long productId) {
        ImageDto imageDto = imageService.uploadImage(file, productId);
        return ResponseEntity.ok(new APIResponse<>(imageDto, "Image uploaded successfully"));
    }

    @PutMapping("/update/{imageId}")
    public ResponseEntity<APIResponse<ImageDto>> updateImage(@PathVariable Long imageId, @RequestParam("file") MultipartFile file) {
        ImageDto imageDto = imageService.updateImage(imageId, file);
        return ResponseEntity.ok(new APIResponse<>(imageDto, "Image updated successfully"));
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<APIResponse<ImageDto>> getImageById(@PathVariable Long imageId) {
        ImageDto imageDto = imageService.getImageById(imageId);
        return ResponseEntity.ok(new APIResponse<>(imageDto, "Image fetched successfully"));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<APIResponse<List<ImageDto>>> getImagesByProduct(@PathVariable Long productId) {
        List<ImageDto> imageDtos = imageService.getImagesByProduct(productId);
        return ResponseEntity.ok(new APIResponse<>(imageDtos, "Images fetched successfully"));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<APIResponse<Void>> deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok(new APIResponse<Void>(null, "Image deleted successfully"));
    }
} 