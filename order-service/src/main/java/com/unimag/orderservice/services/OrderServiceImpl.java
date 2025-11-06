package com.unimag.orderservice.services;

import com.unimag.orderservice.commands.ReserveInventoryCommand;
import com.unimag.orderservice.dtos.CreateOrderRequest;
import com.unimag.orderservice.entities.Order;
import com.unimag.orderservice.enums.StatusEnum;
import com.unimag.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final StreamBridge streamBridge;

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

    public void sendCommand(Order order) {
        ReserveInventoryCommand command = new ReserveInventoryCommand(order.getOrderId().toString(), order.getProductId(), order.getQuantity());
        streamBridge.send("reserveOrder-out-0", command);
    }
}
