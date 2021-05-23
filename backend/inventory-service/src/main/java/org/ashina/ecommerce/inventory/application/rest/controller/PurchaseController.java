package org.ashina.ecommerce.inventory.application.rest.controller;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.inventory.application.command.model.CreatePurchaseCommand;
import org.ashina.ecommerce.inventory.application.rest.dto.CreatePurchaseDto;
import org.ashina.ecommerce.sharedkernel.command.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PurchaseController {

    private final CommandGateway commandGateway;

    @PostMapping("/api/v1/purchases")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody CreatePurchaseDto dto) {
        CreatePurchaseCommand command = newCreatePurchaseCommand(dto);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CreatePurchaseCommand newCreatePurchaseCommand(CreatePurchaseDto dto) {
        CreatePurchaseCommand command = new CreatePurchaseCommand();
        command.setSupplierId(dto.getSupplierId());
        command.setProductId(dto.getProductId());
        command.setQuantity(dto.getQuantity());
        return command;
    }

}
