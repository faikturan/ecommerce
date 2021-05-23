package org.ashina.ecommerce.inventory.application.rest.controller;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.inventory.application.command.model.CreateSupplierCommand;
import org.ashina.ecommerce.inventory.application.command.model.UpdateSupplierCommand;
import org.ashina.ecommerce.inventory.application.rest.dto.CreateSupplierDto;
import org.ashina.ecommerce.inventory.application.rest.dto.UpdateSupplierDto;
import org.ashina.ecommerce.sharedkernel.command.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SupplierController {

    private final CommandGateway commandGateway;

    @PostMapping("/api/v1/suppliers")
    public ResponseEntity<Void> createSupplier(@Valid @RequestBody CreateSupplierDto dto) {
        CreateSupplierCommand command = newCreateSupplierCommand(dto);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CreateSupplierCommand newCreateSupplierCommand(CreateSupplierDto dto) {
        CreateSupplierCommand command = new CreateSupplierCommand();
        command.setFullName(dto.getFullName());
        command.setPhoneNumber(dto.getPhoneNumber());
        return command;
    }

    @PutMapping("/api/v1/suppliers")
    public ResponseEntity<Void> updateSupplier(@Valid @RequestBody UpdateSupplierDto dto) {
        UpdateSupplierCommand command = newUpdateSupplierCommand(dto);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private UpdateSupplierCommand newUpdateSupplierCommand(UpdateSupplierDto dto) {
        UpdateSupplierCommand command = new UpdateSupplierCommand();
        command.setId(dto.getId());
        command.setFullName(dto.getFullName());
        command.setPhoneNumber(dto.getPhoneNumber());
        return command;
    }

}
