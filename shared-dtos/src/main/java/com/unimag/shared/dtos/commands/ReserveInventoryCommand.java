package com.unimag.shared.dtos.commands;

public record ReserveInventoryCommand(
        String orderId,
        String productId,
        int quantity)
{ }

