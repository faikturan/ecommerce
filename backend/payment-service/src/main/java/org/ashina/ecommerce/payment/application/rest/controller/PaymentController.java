package org.ashina.ecommerce.payment.application.rest.controller;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.payment.application.command.model.CreatePaymentCommand;
import org.ashina.ecommerce.payment.application.rest.dto.CreatePaymentDto;
import org.ashina.ecommerce.sharedkernel.command.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final CommandGateway commandGateway;

    @GetMapping("/api/v1/payments")
    public ResponseEntity<Void> createPaymentDto(@Valid @RequestBody CreatePaymentDto dto) {
        CreatePaymentCommand command = newCreatePaymentCommand(dto);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CreatePaymentCommand newCreatePaymentCommand(CreatePaymentDto dto) {
        return CreatePaymentCommand.builder()
                .orderId(dto.getOrderId())
                .amount(dto.getAmount())
                .build();
    }
}
