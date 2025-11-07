package com.unimag.inventoryservice.listeners;

import com.unimag.inventoryservice.services.InventoryService;
import com.unimag.shared.dtos.commands.ReserveInventoryCommand;
import com.unimag.shared.dtos.events.InventoryRejectedEvent;
import com.unimag.shared.dtos.events.InventoryReservedEvent;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@AllArgsConstructor
@Configuration
public class InventoryListeners {
    private final InventoryService inventoryService;
    private final StreamBridge streamBridge;

    @Bean
    public Consumer<ReserveInventoryCommand> reserveInventory() {
        return command -> {
            int quantityAvailable = inventoryService.checkQuantity(command);

            if (quantityAvailable >= command.quantity()){
                inventoryService.updateQuantity(command, quantityAvailable);
                InventoryReservedEvent inventoryReservedEvent = new InventoryReservedEvent(command.orderId());
                streamBridge.send("orderInProgress-out-0", inventoryReservedEvent);
                return;
            }

            InventoryRejectedEvent inventoryRejectedEvent = new InventoryRejectedEvent(command.orderId());
            streamBridge.send("orderRejected-out-0", inventoryRejectedEvent);
        };
    }
}
