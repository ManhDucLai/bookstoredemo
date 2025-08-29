package com.fds.flexdata.plugin.bookstoredemo.controller;

import com.fds.flexdata.plugin.bookstoredemo.Entity.User;
import com.fds.flexdata.plugin.bookstoredemo.config.JwtUtil;
import com.fds.flexdata.plugin.bookstoredemo.dto.AuthResponse;
import com.fds.flexdata.plugin.bookstoredemo.dto.LoginRequest;
import com.fds.flexdata.plugin.bookstoredemo.exception.InvalidCredentialsException;
import com.fds.flexdata.plugin.bookstoredemo.dto.RegisterRequest;
import com.fds.flexdata.plugin.bookstoredemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
