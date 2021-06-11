package org.ashina.ecommerce.product.application.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.product.application.command.model.PurchaseProductCommand;
import org.ashina.ecommerce.product.application.command.model.RefundProductsCommand;
import org.ashina.ecommerce.product.application.command.model.ReserveProductsCommand;
import org.ashina.ecommerce.product.application.rest.dto.PurchaseProductDto;
import org.ashina.ecommerce.product.application.rest.dto.RefundProductsDto;
import org.ashina.ecommerce.product.application.rest.dto.ReserveProductsDto;
import org.ashina.ecommerce.sharedkernel.command.gateway.DefaultCommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final DefaultCommandGateway commandGateway;

    @PostMapping(value = "/api/v1/inventory", params = "action=purchase")
    public ResponseEntity<Void> purchaseProduct(@Valid @RequestBody PurchaseProductDto dto) {
        PurchaseProductCommand command = newPurchaseProductCommand(dto);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private PurchaseProductCommand newPurchaseProductCommand(PurchaseProductDto dto) {
        return PurchaseProductCommand.builder()
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .build();
    }

    @PostMapping(value = "/api/v1/inventory", params = "action=reserve")
    public ResponseEntity<Void> reserveProducts(@Valid @RequestBody ReserveProductsDto dto) {
        ReserveProductsCommand command = newReserveProductsCommand(dto);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ReserveProductsCommand newReserveProductsCommand(ReserveProductsDto dto) {
        List<ReserveProductsCommand.Line> commandLines = new ArrayList<>();
        dto.getLines().forEach(dtoLine -> {
            commandLines.add(ReserveProductsCommand.Line.builder()
                    .productId(dtoLine.getProductId())
                    .quantity(dtoLine.getQuantity())
                    .build()
            );
        });
        return ReserveProductsCommand.builder()
                .lines(commandLines)
                .build();
    }

    @PostMapping(value = "/api/v1/inventory", params = "action=refund")
    public ResponseEntity<Boolean> refundProduct(@Valid @RequestBody RefundProductsDto dto) {
        RefundProductsCommand command = newRefundProductsCommand(dto);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private RefundProductsCommand newRefundProductsCommand(RefundProductsDto dto) {
        List<RefundProductsCommand.Line> commandLines = new ArrayList<>();
        dto.getLines().forEach(dtoLine -> {
            commandLines.add(RefundProductsCommand.Line.builder()
                    .productId(dtoLine.getProductId())
                    .quantity(dtoLine.getQuantity())
                    .build()
            );
        });
        return RefundProductsCommand.builder()
                .lines(commandLines)
                .build();
    }
}
