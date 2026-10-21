package com.koushal.catalog.service.impl;

import com.koushal.catalog.dto.CategoryRequestDto;
import com.koushal.catalog.dto.CategoryResponseDto;
import com.koushal.catalog.entity.Category;
import com.koushal.catalog.exception.ResourceNotFoundException;
import com.koushal.catalog.repository.CategoryRepository;
import com.koushal.catalog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {
        Category category = modelMapper.map(requestDto, Category.class);

        Category savedctedCategory = categoryRepository.save(category);

        return modelMapper.map(savedctedCategory, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("category with id " + id + " not found"));

        return modelMapper.map(category, CategoryResponseDto.class);

    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream().map(category ->
                        modelMapper.map(category,
                                CategoryResponseDto.class)).toList();
    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto requestDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category with id " + id + " not found"));

        existingCategory.setName(requestDto.getName());
        
        Category updatedCategory = categoryRepository.save(existingCategory);

        return modelMapper.map(updatedCategory, CategoryResponseDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        categoryRepository.delete(existingCategory);
    }
}
