package com.byui.ibcmarketplace.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.byui.ibcmarketplace.model.User;
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

    @Autowired
    private ModelMapper modelMapper;

    private UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @PostMapping
    public ResponseEntity<APIResponse<UserDto>> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.ok(new APIResponse<>(true, "User created successfully", toDto(user)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<UserDto>> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        User user = userService.updateUser(id, request);
        return ResponseEntity.ok(new APIResponse<>(true, "User updated successfully", toDto(user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new APIResponse<>(true, "User deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<UserDto>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "User fetched successfully", toDto(user)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<APIResponse<UserDto>> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(new APIResponse<>(true, "User fetched successfully", toDto(user)));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<UserDto>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> dtos = users.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(new APIResponse<>(true, "Users fetched successfully", dtos));
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<APIResponse<UserProfileDto>> getUserProfile(@PathVariable Long id) {
        UserProfileDto profile = userService.getUserProfile(id);
        return ResponseEntity.ok(new APIResponse<>(true, "User profile fetched successfully", profile));
    }
} 