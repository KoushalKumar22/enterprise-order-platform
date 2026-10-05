package com.koushal.orderplatform.service.impl;

import com.koushal.orderplatform.entity.Inventory;
import com.koushal.orderplatform.entity.Product;
import com.koushal.orderplatform.repository.InventoryRepository;
import com.koushal.orderplatform.repository.ProductRepository;
import com.koushal.orderplatform.service.InventoryService;

import java.time.LocalDateTime;
import java.util.List;

public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Inventory createInventory(Inventory inventory) {

        if (inventory.getProduct() == null ||
                inventory.getProduct().getId() == null) {

            throw new RuntimeException("Product is required");
        }

        Long productId = inventory.getProduct().getId();

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with id: " + productId));

        inventory.setProduct(product);

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

        existingInventory.setAvaliableQuantity(
                inventory.getAvaliableQuantity());

        existingInventory.setUpdatedAt(LocalDateTime.now());

        return inventoryRepository.save(existingInventory);
    }

    @Override
    public boolean isProductAvailable(
            Long productId,
            Integer quantity) {

        Inventory inventory = inventoryRepository.findById(productId)
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
