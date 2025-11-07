package com.unimag.orderservice.services;

import com.unimag.orderservice.dtos.CreateOrderRequest;
import com.unimag.orderservice.entities.Order;
import com.unimag.orderservice.enums.StatusEnum;
import com.unimag.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(CreateOrderRequest createOrderRequest) {
        Order order = Order.builder()
                .productId(createOrderRequest.productId())
                .quantity(createOrderRequest.quantity())
                .createdAt(LocalDateTime.now())
                .status(StatusEnum.CREATED)
                .build();

        return orderRepository.save(order);
    }
}
