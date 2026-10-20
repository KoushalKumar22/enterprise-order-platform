package com.koushal.inventory.controller;

import com.koushal.inventory.dto.InventoryRequestDto;
import com.koushal.inventory.dto.InventoryResponseDto;
import com.koushal.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<InventoryResponseDto> createInventory(
            @Valid @RequestBody InventoryRequestDto requestDto
    ){
        InventoryResponseDto response =
                inventoryService.createInventory(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")

    public ResponseEntity<InventoryResponseDto> getInventoryById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(
                inventoryService.getInventoryById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> getInventoryByCategory() {
        return ResponseEntity.ok(
                inventoryService.getAllInventory()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDto> updateInventory(
            @PathVariable Long id,
            @Valid @RequestBody InventoryRequestDto requestDto
    ){
        return ResponseEntity.ok(
                inventoryService.updateInventory(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(
            @PathVariable Long id
    ) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
