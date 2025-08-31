package com.example.bookstoredemo.repository;

import com.example.bookstoredemo.Entity.Category;
import com.example.bookstoredemo.dto.CategoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Lấy danh mục kèm số sách trong mỗi danh mục
    @Query(value = """
            SELECT c.id AS id, c.name AS name, c.slug AS slug, COUNT(b.id) AS productCount
            FROM categories c
            LEFT JOIN book_categories bc ON c.id = bc.category_id
            LEFT JOIN books b ON bc.book_id = b.id
            GROUP BY c.id, c.name, c.slug
            """, nativeQuery = true)
    List<CategoryInfo> findCategoriesWithBookCount();
}
