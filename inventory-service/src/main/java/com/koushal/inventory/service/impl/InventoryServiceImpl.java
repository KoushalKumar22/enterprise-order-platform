package com.koushal.inventory.service.impl;

import com.koushal.inventory.dto.InventoryRequestDto;
import com.koushal.inventory.dto.InventoryResponseDto;
import com.koushal.inventory.entity.Inventory;
import com.koushal.inventory.exception.ResourceNotFoundException;
import com.koushal.inventory.repository.InventoryRepository;
import com.koushal.inventory.service.InventoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    public InventoryServiceImpl(
            InventoryRepository inventoryRepository,
            ModelMapper modelMapper
    ) {
        this.inventoryRepository = inventoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public InventoryResponseDto createInventory(
            InventoryRequestDto requestDto
    ) {
        Inventory inventory = modelMapper.map(
                requestDto,
                Inventory.class
        );

        inventory.setId(null);
        inventory.setReservedQuantity(0);

        LocalDateTime now = LocalDateTime.now();
        inventory.setCreatedAt(now);
        inventory.setUpdatedAt(now);

        Inventory savedInventory =
                inventoryRepository.save(inventory);

        return modelMapper.map(
                savedInventory,
                InventoryResponseDto.class
        );
    }

    @Override
    public InventoryResponseDto getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory with id " + id + " not found"
                        ));

        return modelMapper.map(
                inventory,
                InventoryResponseDto.class
        );
    }

    @Override
    public List<InventoryResponseDto> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(inventory ->
                        modelMapper.map(
                                inventory,
                                InventoryResponseDto.class
                        ))
                .toList();
    }

    @Override
    public InventoryResponseDto updateInventory(
            Long id,
            InventoryRequestDto requestDto
    ) {
        Inventory existingInventory =
                inventoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory with id "
                                                + id + " not found"
                                ));

        // Update only fields that this request is allowed to change.
        existingInventory.setProductId(requestDto.getProductId());
        existingInventory.setAvailableQuantity(
                requestDto.getAvailableQuantity()
        );
        existingInventory.setUpdatedAt(LocalDateTime.now());

        Inventory updatedInventory =
                inventoryRepository.save(existingInventory);

        return modelMapper.map(
                updatedInventory,
                InventoryResponseDto.class
        );
    }

    @Override
    public boolean isProductAvailable(
            Long productId,
            Integer quantity
    ) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }

        Inventory inventory =
                inventoryRepository.findByProductId(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventory not found for product id: "
                                                + productId
                                ));

        return inventory.getAvailableQuantity() >= quantity;
    }

    @Override
    public void deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory with id " + id + " not found"
                        ));

        inventoryRepository.delete(inventory);
    }
}