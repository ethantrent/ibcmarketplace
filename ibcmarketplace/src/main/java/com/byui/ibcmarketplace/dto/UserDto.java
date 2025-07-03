package com.byui.ibcmarketplace.dto;

import lombok.Data;

/**
 * DTO for exposing user information to the frontend.
 * Does not include password or sensitive fields.
 */
@Data
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
} 