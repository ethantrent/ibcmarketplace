package com.byui.ibcmarketplace.service.user;

import java.util.List;

import com.byui.ibcmarketplace.dto.UserProfileDto;
import com.byui.ibcmarketplace.model.User;
import com.byui.ibcmarketplace.request.CreateUserRequest;
import com.byui.ibcmarketplace.request.UpdateUserRequest;

public interface IUserService {
    User createUser(CreateUserRequest request);
    User updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
    User getUserById(Long id);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    UserProfileDto getUserProfile(Long userId);
} 