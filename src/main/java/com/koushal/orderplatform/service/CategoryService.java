package com.koushal.orderplatform.service;

import com.koushal.orderplatform.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);
}
