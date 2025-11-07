package com.unimag.inventoryservice.listeners;

import com.unimag.shared.dtos.ReserveInventoryCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class InventoryListeners {

    @Bean
    public Consumer<ReserveInventoryCommand> reserveInventory() {
        return command -> {

            System.out.println("[inventory-service] Recibido ReserveInventoryCommand: " + command);
            // TODO: implementar idempotencia y persistencia
        };
    }
}
