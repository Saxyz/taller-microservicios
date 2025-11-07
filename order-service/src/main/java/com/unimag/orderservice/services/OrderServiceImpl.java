package com.unimag.orderservice.services;

import com.unimag.orderservice.dtos.CreateOrderRequest;
import com.unimag.orderservice.entities.Order;
import com.unimag.orderservice.enums.StatusEnum;
import com.unimag.orderservice.exceptions.OrderNotFoundException;
import com.unimag.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

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

    @Override
    public Order updateOrderStatusToPendingPayment(String orderId) {
        Order order = orderRepository.findById(UUID.fromString(orderId)).orElseThrow(() -> new OrderNotFoundException("Order with id: " + orderId + " not found"));

        order.setStatus(StatusEnum.PENDING_PAYMENT);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderStatusToRecjected(String orderId) {
        Order order = orderRepository.findById(UUID.fromString(orderId)).orElseThrow(() -> new OrderNotFoundException("Order with id: " + orderId + " not found"));

        order.setStatus(StatusEnum.REJECTED);
        return orderRepository.save(order);
    }
}
