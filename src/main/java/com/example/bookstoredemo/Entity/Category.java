package com.example.bookstoredemo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String slug;

    @Column(name = "parent_id")
    private Long parentId;

    // số sản phẩm trong danh mục (tính động hoặc lưu cache)
    @Transient
    private Long productCount;

    // getter & setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public Long getProductCount() { return productCount; }
    public void setProductCount(Long productCount) { this.productCount = productCount; }
}
