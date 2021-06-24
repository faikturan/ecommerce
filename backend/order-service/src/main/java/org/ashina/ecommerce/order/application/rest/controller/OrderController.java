package org.ashina.ecommerce.order.application.rest.controller;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.order.application.command.FulfillmentOrderCommand;
import org.ashina.ecommerce.order.application.rest.dto.FulfillmentOrderDto;
import org.ashina.ecommerce.order.infrastructure.security.SecurityContextHelper;
import org.ashina.ecommerce.sharedkernel.command.gateway.DefaultCommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final DefaultCommandGateway commandGateway;

    @PostMapping(value = "/api/v1/orders", params = "action=fulfillment")
    public ResponseEntity<Void> fulfillmentOrder(@Valid @RequestBody FulfillmentOrderDto dto,
                                                 @AuthenticationPrincipal Jwt jwt) {
        FulfillmentOrderCommand command = newFulfillmentOrderCommand(dto, jwt);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private FulfillmentOrderCommand newFulfillmentOrderCommand(FulfillmentOrderDto dto, Jwt jwt) {
        return FulfillmentOrderCommand.builder()
                .customerId(SecurityContextHelper.currentCustomerId(jwt))
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .build();
    }
}
