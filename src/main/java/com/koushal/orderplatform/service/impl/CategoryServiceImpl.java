package com.koushal.orderplatform.service.impl;

import com.koushal.orderplatform.entity.Category;
import com.koushal.orderplatform.repository.CategoryRepository;
import com.koushal.orderplatform.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category with id " + id + " not found"));

    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category with id " + id + " not found"));

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category with id " + id + " not found"));
        categoryRepository.delete(existingCategory);
    }
}
