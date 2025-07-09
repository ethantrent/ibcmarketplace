package com.byui.ibcmarketplace.service.user;

import java.util.List;

import com.byui.ibcmarketplace.dto.UserDto;
import com.byui.ibcmarketplace.dto.UserProfileDto;
import com.byui.ibcmarketplace.request.CreateUserRequest;
import com.byui.ibcmarketplace.request.UpdateUserRequest;

public interface IUserService {
    UserDto createUser(CreateUserRequest request);
    UserDto updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
    List<UserDto> getAllUsers();
    UserProfileDto getUserProfile(Long userId);
} 