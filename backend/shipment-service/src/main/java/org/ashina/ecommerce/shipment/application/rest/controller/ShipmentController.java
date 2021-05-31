package org.ashina.ecommerce.shipment.application.rest.controller;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.sharedkernel.command.gateway.CommandGateway;
import org.ashina.ecommerce.shipment.application.command.model.CreateShipmentCommand;
import org.ashina.ecommerce.shipment.application.rest.dto.CreateShipmentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ShipmentController {

    private final CommandGateway commandGateway;

    @GetMapping("/api/v1/shipments")
    public ResponseEntity<Void> createShipmentDto(@Valid @RequestBody CreateShipmentDto dto) {
        CreateShipmentCommand command = newCreateShipmentCommand(dto);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CreateShipmentCommand newCreateShipmentCommand(CreateShipmentDto dto) {
        CreateShipmentCommand command = new CreateShipmentCommand();
        command.setOrderId(dto.getOrderId());
        command.setFullName(dto.getFullName());
        command.setPhoneNo(dto.getPhoneNo());
        command.setAddress(dto.getAddress());
        return command;
    }
}
