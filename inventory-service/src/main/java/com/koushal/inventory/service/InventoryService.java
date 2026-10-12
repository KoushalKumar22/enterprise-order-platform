package com.koushal.inventory.service;

import com.koushal.inventory.dto.InventoryRequestDto;
import com.koushal.inventory.dto.InventoryResponseDto;

import java.util.List;

public interface InventoryService {

    InventoryResponseDto createInventory(InventoryRequestDto requestDto);

    InventoryResponseDto getInventoryById(Long id);

    List<InventoryResponseDto> getAllInventory();

    InventoryResponseDto updateInventory(
            Long id,
            InventoryRequestDto requestDto
    );

    boolean isProductAvailable(Long productId, Integer quantity);

    void deleteInventory(Long id);
}