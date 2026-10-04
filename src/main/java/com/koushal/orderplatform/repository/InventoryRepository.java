package com.koushal.orderplatform.repository;

import com.koushal.orderplatform.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
