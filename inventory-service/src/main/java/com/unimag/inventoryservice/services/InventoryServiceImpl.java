package com.unimag.inventoryservice.services;

import com.unimag.inventoryservice.entities.InventoryItem;
import com.unimag.inventoryservice.repositories.InventoryItemRepository;
import com.unimag.shared.dtos.commands.ReserveInventoryCommand;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements  InventoryService {
    private final InventoryItemRepository inventoryItemRepository;

    @Override
    public int checkQuantity(ReserveInventoryCommand command) {
        InventoryItem inventoryItem = inventoryItemRepository.findByProductId(command.productId())
                .orElseThrow(()-> new EntityNotFoundException("Product not found"));
        return inventoryItem.getAvailableQuantity();
    }

    @Override
    public void updateQuantity(ReserveInventoryCommand command, int quantityAvailable) {
        InventoryItem inventoryItem = inventoryItemRepository.findByProductId(command.productId())
                .orElseThrow(()-> new EntityNotFoundException("Product not found"));
        inventoryItem.setAvailableQuantity(quantityAvailable - command.quantity());
        inventoryItemRepository.save(inventoryItem);
    }
}


