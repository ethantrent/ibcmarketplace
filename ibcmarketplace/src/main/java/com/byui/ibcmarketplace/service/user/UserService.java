package com.byui.ibcmarketplace.service.user;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.byui.ibcmarketplace.dto.UserDto;
import com.byui.ibcmarketplace.dto.UserProfileDto;
import com.byui.ibcmarketplace.model.User;
import com.byui.ibcmarketplace.repository.UserRepository;
import com.byui.ibcmarketplace.request.CreateUserRequest;
import com.byui.ibcmarketplace.request.UpdateUserRequest;
import com.byui.ibcmarketplace.service.cart.ICartService;
import com.byui.ibcmarketplace.service.order.IOrderService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ICartService cartService;
    private final IOrderService orderService;

    @Override
    public User createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        // TODO: Hash password before saving in production
        user.setPassword(request.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("User not found with id: " + userId));
        UserProfileDto profile = new UserProfileDto();
        profile.setUser(toDto(user));
        profile.setCart(cartService.getCartByUserId(userId));
        profile.setOrders(orderService.getOrdersByUserId(userId));
        return profile;
    }

    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
} 