package com.unimag.orderservice.commands;

public record ReserveInventoryCommand(
        String orderId,
        String productId,
        int quantity
) {
}
