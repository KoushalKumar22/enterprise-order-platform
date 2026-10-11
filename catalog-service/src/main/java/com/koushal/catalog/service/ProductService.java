package com.koushal.catalog.service;

import com.koushal.catalog.dto.ProductRequestDto;
import com.koushal.catalog.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto requestDto);

    ProductResponseDto getProductById(Long id);

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto updateProduct(
            Long id,
            ProductRequestDto requestDto
    );

    void deleteProduct(Long id);
}