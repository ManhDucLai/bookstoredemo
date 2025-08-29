package com.fds.flexdata.plugin.bookstoredemo.controller;

import com.fds.flexdata.plugin.bookstoredemo.dto.CategoryResponse;
import com.fds.flexdata.plugin.bookstoredemo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getCategories() {
        return categoryService.getAllCategories();
    }
}