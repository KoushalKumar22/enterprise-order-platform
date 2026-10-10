package com.koushal.catalog.service;

import com.koushal.catalog.dto.CategoryRequestDto;
import com.koushal.catalog.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto requestDto);

    CategoryResponseDto getCategoryById(Long id);

    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto updateCategory(Long id, CategoryRequestDto category);

    void deleteCategory(Long id);
}
