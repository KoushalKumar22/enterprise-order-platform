package com.koushal.catalog.service;

import com.koushal.catalog.entity.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);
    Product getProduct(Long id);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
