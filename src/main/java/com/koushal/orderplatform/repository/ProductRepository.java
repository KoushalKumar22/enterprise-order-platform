package com.koushal.orderplatform.repository;

import com.koushal.orderplatform.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
