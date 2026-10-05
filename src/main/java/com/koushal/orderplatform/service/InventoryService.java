package com.koushal.orderplatform.service;

import com.koushal.orderplatform.entity.Inventory;

import java.util.List;

public interface InventoryService {

    Inventory createInventory(Inventory inventory);

    Inventory getInventoryById(Long id);

    List<Inventory> getAllInventory();

    Inventory updateInventory(Long id, Inventory inventory);

    boolean isProductAvailable(Long productId, Integer quantity);

    void deleteInventory(Long id);
}
