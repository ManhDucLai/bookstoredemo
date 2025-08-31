package com.example.bookstoredemo.service;

import com.example.bookstoredemo.dto.CategoryInfo;
import com.example.bookstoredemo.dto.CategoryResponse;
import com.example.bookstoredemo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Annotation quan trọng để Spring nhận diện đây là một Bean
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getAllCategories() {
        // 1. Gọi repository để lấy dữ liệu thô (dưới dạng CategoryInfo)
        List<CategoryInfo> categoriesWithCount = categoryRepository.findCategoriesWithBookCount();

        // 2. Chuyển đổi (map) từ CategoryInfo sang CategoryResponse để trả về cho client
        return categoriesWithCount.stream()
                .map(info -> new CategoryResponse(info.getId(), info.getName(), info.getSlug(), info.getProductCount()))
                .collect(Collectors.toList());
    }
}