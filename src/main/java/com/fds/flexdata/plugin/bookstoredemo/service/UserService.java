package com.fds.flexdata.plugin.bookstoredemo.service;

import com.fds.flexdata.plugin.bookstoredemo.Entity.User;
import com.fds.flexdata.plugin.bookstoredemo.dto.RegisterRequest;
import com.fds.flexdata.plugin.bookstoredemo.exception.UserAlreadyExistsException;
import com.fds.flexdata.plugin.bookstoredemo.exception.UserNotFoundException;
import com.fds.flexdata.plugin.bookstoredemo.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username)) {
            throw new UserAlreadyExistsException("Username '" + request.username + "' đã được sử dụng.");
        }
        if (userRepository.existsByEmail(request.email)) {
            throw new UserAlreadyExistsException("Email '" + request.email + "' đã được sử dụng.");
        }

        User user = new User();
        user.setUsername(request.username);
        user.setEmail(request.email);
        user.setFullName(request.fullName);
        user.setPasswordHash(passwordEncoder.encode(request.password));

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng với username: '" + username + "'"));
    }
}
