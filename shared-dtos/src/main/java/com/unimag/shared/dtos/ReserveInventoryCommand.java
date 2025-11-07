package com.unimag.shared.dtos;

public record ReserveInventoryCommand(
        String orderId,
        String productId,
        int quantity)
{ }

