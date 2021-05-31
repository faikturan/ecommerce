package org.ashina.ecommerce.order.application.rest.controller;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.order.application.command.CancelOrderCommand;
import org.ashina.ecommerce.order.application.command.FulfillmentOrderCommand;
import org.ashina.ecommerce.order.infrastructure.security.SecurityContextHelper;
import org.ashina.ecommerce.sharedkernel.command.gateway.DefaultCommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final DefaultCommandGateway commandGateway;

    @PostMapping("/api/v1/orders")
    public ResponseEntity<Void> fulfillmentOrder(@AuthenticationPrincipal Jwt jwt) {
        FulfillmentOrderCommand command = newFulfillmentOrderCommand(jwt);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private FulfillmentOrderCommand newFulfillmentOrderCommand(Jwt jwt) {
        FulfillmentOrderCommand command = new FulfillmentOrderCommand();
        command.setCustomerId(SecurityContextHelper.currentCustomerId(jwt));
        return command;
    }

    @PostMapping("/api/v1/orders/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable String orderId,
                                            @AuthenticationPrincipal Jwt jwt) {
        CancelOrderCommand command = newCancelOrderCommand(orderId, jwt);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CancelOrderCommand newCancelOrderCommand(String orderId, Jwt jwt) {
        CancelOrderCommand command = new CancelOrderCommand();
        command.setCustomerId(SecurityContextHelper.currentCustomerId(jwt));
        command.setOrderId(orderId);
        return command;
    }

}
