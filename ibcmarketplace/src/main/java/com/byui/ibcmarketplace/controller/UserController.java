package com.byui.ibcmarketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byui.ibcmarketplace.dto.APIResponse;
import com.byui.ibcmarketplace.dto.UserDto;
import com.byui.ibcmarketplace.dto.UserProfileDto;
import com.byui.ibcmarketplace.request.CreateUserRequest;
import com.byui.ibcmarketplace.request.UpdateUserRequest;
import com.byui.ibcmarketplace.service.user.IUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping
    public ResponseEntity<APIResponse<UserDto>> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDto user = userService.createUser(request);
        return ResponseEntity.ok(new APIResponse<>(user, "User created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<UserDto>> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        UserDto user = userService.updateUser(id, request);
        return ResponseEntity.ok(new APIResponse<>(user, "User updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new APIResponse<Void>(null, "User deleted successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<UserDto>> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(new APIResponse<>(user, "User fetched successfully"));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<APIResponse<UserDto>> getUserByEmail(@PathVariable String email) {
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(new APIResponse<>(user, "User fetched successfully"));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(new APIResponse<>(users, "Users fetched successfully"));
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<APIResponse<UserProfileDto>> getUserProfile(@PathVariable Long id) {
        UserProfileDto profile = userService.getUserProfile(id);
        return ResponseEntity.ok(new APIResponse<>(profile, "User profile fetched successfully"));
    }
} 