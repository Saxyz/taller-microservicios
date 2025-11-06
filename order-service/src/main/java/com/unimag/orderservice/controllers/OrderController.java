package com.unimag.orderservice.controllers;

import com.unimag.orderservice.dtos.CreateOrderRequest;
import com.unimag.orderservice.entities.Order;
import com.unimag.orderservice.enums.StatusEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {

    }
}
