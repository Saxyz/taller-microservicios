package com.unimag.shared.dtos.events;

import java.math.BigDecimal;

public record InventoryReservedEvent(
        String orderId,
        String productId,
        int quantity,
        BigDecimal totalAmount
) {
}
