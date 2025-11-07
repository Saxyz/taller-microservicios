package com.unimag.inventoryservice.services;

import com.unimag.shared.dtos.commands.ReserveInventoryCommand;

public interface InventoryService {
    int checkQuantity(ReserveInventoryCommand command);
    void updateQuantity(ReserveInventoryCommand command, int quantityAvailable);
}

