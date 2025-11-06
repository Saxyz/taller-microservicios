package com.unimag.orderservice.services;

import com.unimag.orderservice.dtos.CreateOrderRequest;
import com.unimag.orderservice.entities.Order;

public interface OrderService {
    Order createOrder(CreateOrderRequest createOrderRequest);
}
