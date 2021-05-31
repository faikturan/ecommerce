package org.ashina.ecommerce.shipment.registry;

import org.ashina.ecommerce.shipment.application.command.handler.CreateShipmentCommandHandler;
import org.ashina.ecommerce.shipment.infrastructure.persistence.repository.ShipmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerRegistry {

    @Bean
    public CreateShipmentCommandHandler createPurchaseCommandHandler(ShipmentRepository shipmentRepository) {
        return new CreateShipmentCommandHandler(shipmentRepository);
    }
}
