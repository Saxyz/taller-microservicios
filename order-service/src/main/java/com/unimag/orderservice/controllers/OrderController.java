package com.unimag.orderservice.controllers;

import com.unimag.orderservice.dtos.CreateOrderRequest;
import com.unimag.orderservice.entities.Order;
import com.unimag.orderservice.services.OrderService;
import com.unimag.shared.dtos.ReserveInventoryCommand;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final StreamBridge streamBridge;

    public OrderController(OrderService orderService, StreamBridge streamBridge) {
        this.orderService = orderService;
        this.streamBridge = streamBridge;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        Order order = orderService.createOrder(createOrderRequest);
        ReserveInventoryCommand command = new ReserveInventoryCommand(order.getOrderId().toString(), order.getProductId(), order.getQuantity());
        streamBridge.send("reserveOrder-out-0", command);
        return  ResponseEntity.ok(order);
    }
}
