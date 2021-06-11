package org.ashina.ecommerce.payment.application.rest.controller;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.payment.application.command.model.ProcessPaymentCommand;
import org.ashina.ecommerce.payment.application.rest.dto.ProcessPaymentDto;
import org.ashina.ecommerce.sharedkernel.command.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final CommandGateway commandGateway;

    @PostMapping("/api/v1/payments")
    public ResponseEntity<Void> processPayment(@Valid @RequestBody ProcessPaymentDto dto) {
        ProcessPaymentCommand command = newProcessPaymentCommand(dto);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ProcessPaymentCommand newProcessPaymentCommand(ProcessPaymentDto dto) {
        return ProcessPaymentCommand.builder()
                .customerId(dto.getCustomerId())
                .orderId(dto.getOrderId())
                .amount(dto.getAmount())
                .build();
    }
}
