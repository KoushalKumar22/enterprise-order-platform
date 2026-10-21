package com.koushal.catalog.service.impl;

import com.koushal.catalog.dto.ProductRequestDto;
import com.koushal.catalog.dto.ProductResponseDto;
import com.koushal.catalog.entity.Category;
import com.koushal.catalog.entity.Product;
import com.koushal.catalog.exception.ResourceNotFoundException;
import com.koushal.catalog.repository.CategoryRepository;
import com.koushal.catalog.repository.ProductRepository;
import com.koushal.catalog.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ModelMapper modelMapper
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductResponseDto createProduct(
            ProductRequestDto requestDto
    ) {
        Category category = categoryRepository
                .findById(requestDto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category with id "
                                        + requestDto.getCategoryId()
                                        + " not found"
                        ));

        Product product = modelMapper.map(
                requestDto,
                Product.class
        );

        product.setId(null);
        product.setCategory(category);

        LocalDateTime now = LocalDateTime.now();
        product.setCreatedAt(now);
        product.setUpdatedAt(now);

        Product savedProduct = productRepository.save(product);

        return modelMapper.map(
                savedProduct,
                ProductResponseDto.class
        );
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product with id " + id + " not found"
                        ));

        return modelMapper.map(
                product,
                ProductResponseDto.class
        );
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product ->
                        modelMapper.map(
                                product,
                                ProductResponseDto.class
                        ))
                .toList();
    }

    @Override
    public ProductResponseDto updateProduct(
            Long id,
            ProductRequestDto requestDto
    ) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product with id " + id + " not found"
                        ));

        Category category = categoryRepository
                .findById(requestDto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category with id "
                                        + requestDto.getCategoryId()
                                        + " not found"
                        ));

        modelMapper.map(requestDto, existingProduct);

        existingProduct.setCategory(category);
        existingProduct.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct =
                productRepository.save(existingProduct);

        return modelMapper.map(
                updatedProduct,
                ProductResponseDto.class
        );
    }

    @Override
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product with id " + id + " not found"
                        ));

        productRepository.delete(existingProduct);
    }
}