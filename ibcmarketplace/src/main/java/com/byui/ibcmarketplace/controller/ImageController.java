package com.byui.ibcmarketplace.controller;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.byui.ibcmarketplace.model.Image;
import com.byui.ibcmarketplace.service.image.IImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService imageService;
    
    @Autowired
    private ModelMapper modelMapper;

    private ImageDto toDto(Image image) {
        return modelMapper.map(image, ImageDto.class);
    }

    @PostMapping("/upload")
    public ResponseEntity<APIResponse<ImageDto>> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productId") Long productId) {
        Image image = imageService.uploadImage(file, productId);
        return ResponseEntity.ok(new APIResponse<>(true, "Image uploaded successfully", toDto(image)));
    }

    @PutMapping("/{imageId}")
    public ResponseEntity<APIResponse<ImageDto>> updateImage(
            @PathVariable Long imageId,
            @RequestParam("file") MultipartFile file) {
        Image image = imageService.updateImage(imageId, file);
        return ResponseEntity.ok(new APIResponse<>(true, "Image updated successfully", toDto(image)));
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        Blob blob = image.getImage();
        byte[] bytes = blob.getBytes(1, (int) blob.length());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getFileType()));
        headers.setContentLength(bytes.length);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<APIResponse<List<ImageDto>>> getImagesByProduct(@PathVariable Long productId) {
        List<Image> images = imageService.getImagesByProduct(productId);
        List<ImageDto> dtos = images.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(new APIResponse<>(true, "Images fetched successfully", dtos));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<APIResponse<Void>> deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok(new APIResponse<>(true, "Image deleted successfully", null));
    }
} 