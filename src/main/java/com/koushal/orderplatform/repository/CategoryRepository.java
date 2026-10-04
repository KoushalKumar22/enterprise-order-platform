package com.koushal.orderplatform.repository;

import com.koushal.orderplatform.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
