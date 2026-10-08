package com.koushal.inventory.service.impl;

import com.koushal.inventory.entity.Inventory;
import com.koushal.inventory.repository.InventoryRepository;
import com.koushal.inventory.service.InventoryService;

import java.time.LocalDateTime;
import java.util.List;

public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory createInventory(Inventory inventory) {

        if (inventory.getProductId() == null) {
            throw new RuntimeException("Product ID is required");
        }

        inventory.setCreatedAt(LocalDateTime.now());
        inventory.setUpdatedAt(LocalDateTime.now());

        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory getInventoryById(Long id) {

        return inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Inventory not found with id: " + id));
    }

    @Override
    public List<Inventory> getAllInventory() {

        return inventoryRepository.findAll();
    }

    @Override
    public Inventory updateInventory(Long id, Inventory inventory) {

        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Inventory not found with id: " + id));

        existingInventory.setAvaliableQuantity(
                inventory.getAvaliableQuantity());

        existingInventory.setUpdatedAt(LocalDateTime.now());

        return inventoryRepository.save(existingInventory);
    }

    @Override
    public boolean isProductAvailable(
            Long productId,
            Integer quantity) {

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Inventory not found for product id: "
                                        + productId));

        return inventory.getAvaliableQuantity() >= quantity;
    }

    @Override
    public void deleteInventory(Long id) {

        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Inventory not found with id: " + id));

        inventoryRepository.delete(existingInventory);
    }
}