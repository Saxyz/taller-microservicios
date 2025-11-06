package com.unimag.orderservice.dtos;

public record CreateOrderRequest(
        String productId,
        int quantity
) {
}
