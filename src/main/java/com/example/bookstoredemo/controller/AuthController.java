package com.example.bookstoredemo.controller;

import com.example.bookstoredemo.Entity.User;
import com.example.bookstoredemo.config.JwtUtil;
import com.example.bookstoredemo.dto.AuthResponse;
import com.example.bookstoredemo.dto.LoginRequest;
import com.example.bookstoredemo.exception.InvalidCredentialsException;
import com.example.bookstoredemo.dto.RegisterRequest;
import com.example.bookstoredemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;


    private final JwtUtil jwtUtil;


    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = userService.register(request);
        return ResponseEntity.ok("Đăng ký thành công: " + user.getUsername());
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.findByUsername(request.username);

        if (!passwordEncoder.matches(request.password, user.getPasswordHash())) {
            throw new InvalidCredentialsException("Tên đăng nhập hoặc mật khẩu không chính xác.");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token, user.getUsername()));
    }
}
