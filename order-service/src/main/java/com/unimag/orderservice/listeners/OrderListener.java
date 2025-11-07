package com.unimag.orderservice.listeners;

import com.unimag.orderservice.services.OrderService;
import com.unimag.shared.dtos.commands.ProcessPaymentCommand;
import com.unimag.shared.dtos.events.InventoryReservedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class OrderListener {

    private final OrderService orderService;
    private final StreamBridge streamBridge;

    @Bean
    public Consumer<InventoryReservedEvent> inventoryReservedEventConsumer() {
        return event -> {
            orderService.updateOrderStatusToPendingPayment(event.orderId());
            streamBridge.send("processPayment-out-1", new ProcessPaymentCommand(event.orderId()));
        };
    }
    
    @Bean
    public Consumer<InventoryReservedEvent> inventoryRejectedEventConsumer() {
        return event -> {
            orderService.updateOrderStatusToRecjected(event.orderId());
        };
    }
}
