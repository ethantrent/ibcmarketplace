package com.byui.ibcmarketplace.dto;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
    String timestamp,
    int status,
    String error,
    String message,
    String path,
    List<ValidationError> errors
) {
    public ErrorResponse(int status, String error, String message, String path) {
        this(Instant.now().toString(), status, error, message, path, null);
    }
    
    public ErrorResponse(int status, String error, String message, String path, List<ValidationError> errors) {
        this(Instant.now().toString(), status, error, message, path, errors);
    }
} 