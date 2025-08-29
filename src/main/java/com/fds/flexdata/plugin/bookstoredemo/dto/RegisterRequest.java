package com.fds.flexdata.plugin.bookstoredemo.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    public String username;
    public String email;
    public String password;
    public String fullName;
}
