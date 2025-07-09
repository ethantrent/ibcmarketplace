package com.byui.ibcmarketplace.controller;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byui.ibcmarketplace.dto.APIResponse;
import com.byui.ibcmarketplace.model.Role;
import com.byui.ibcmarketplace.model.User;
import com.byui.ibcmarketplace.repository.RoleRepository;
import com.byui.ibcmarketplace.repository.UserRepository;
import com.byui.ibcmarketplace.security.CookieUtil;
import com.byui.ibcmarketplace.security.CustomUserDetails;
import com.byui.ibcmarketplace.security.CustomUserDetailsService;
import com.byui.ibcmarketplace.security.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CookieUtil cookieUtil;

    @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> login(@Valid @RequestBody AuthRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(userDetails);
            cookieUtil.createJwtCookie(response, jwt);
            return ResponseEntity.ok(new APIResponse<>(null, "Login successful"));
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new com.byui.ibcmarketplace.exception.AuthenticationException("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse<String>> register(@Valid @RequestBody RegisterRequest request, HttpServletResponse response) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role userRole = roleRepository.findByName("USER").orElseGet(() -> roleRepository.save(new Role("USER")));
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String jwt = jwtUtil.generateToken(userDetails);
        cookieUtil.createJwtCookie(response, jwt);
        return ResponseEntity.ok(new APIResponse<>(null, "Registration successful"));
    }

    @PostMapping("/logout")
    public ResponseEntity<APIResponse<String>> logout(HttpServletResponse response) {
        cookieUtil.clearJwtCookie(response);
        return ResponseEntity.ok(new APIResponse<>(null, "Logout successful"));
    }

    // DTOs for auth
    public static class AuthRequest {
        @jakarta.validation.constraints.Email
        @jakarta.validation.constraints.NotBlank
        private String email;
        @jakarta.validation.constraints.NotBlank
        private String password;
        // getters and setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    public static class RegisterRequest {
        @jakarta.validation.constraints.Email
        @jakarta.validation.constraints.NotBlank
        private String email;
        @jakarta.validation.constraints.NotBlank
        private String password;
        @jakarta.validation.constraints.NotBlank
        private String firstName;
        @jakarta.validation.constraints.NotBlank
        private String lastName;
        // getters and setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
    }
} 