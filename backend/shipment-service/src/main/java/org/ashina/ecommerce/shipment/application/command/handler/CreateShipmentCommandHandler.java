package org.ashina.ecommerce.shipment.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.ashina.ecommerce.shipment.application.command.model.CreateShipmentCommand;
import org.ashina.ecommerce.shipment.domain.Shipment;
import org.ashina.ecommerce.shipment.infrastructure.persistence.repository.ShipmentRepository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class CreateShipmentCommandHandler implements CommandHandler<CreateShipmentCommand, Void> {

    private final ShipmentRepository shipmentRepository;

    @Override
    public Class<?> support() {
        return CreateShipmentCommand.class;
    }

    @Override
    @Transactional
    public Void handle(CreateShipmentCommand command) {
        // Save shipment
        Shipment shipment = new Shipment(DomainEntityIdentifierGenerator.uuid());
        shipment.setOrderId(command.getOrderId());
        shipment.setFullName(command.getFullName());
        shipment.setPhoneNo(command.getPhoneNo());
        shipment.setAddress(command.getAddress());
        shipmentRepository.save(shipment);

        return null;
    }
}
