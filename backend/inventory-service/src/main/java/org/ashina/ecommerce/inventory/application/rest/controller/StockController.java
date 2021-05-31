package org.ashina.ecommerce.inventory.application.rest.controller;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.inventory.application.command.model.PurchaseProductCommand;
import org.ashina.ecommerce.inventory.application.query.model.GetStocksQuery;
import org.ashina.ecommerce.inventory.application.query.model.GetStocksView;
import org.ashina.ecommerce.inventory.application.rest.dto.PurchaseProductDto;
import org.ashina.ecommerce.sharedkernel.command.gateway.CommandGateway;
import org.ashina.ecommerce.sharedkernel.query.gateway.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;

    @GetMapping("/api/v1/stocks")
    public ResponseEntity<GetStocksView> getStocks(@RequestParam List<String> productIds) {
        GetStocksQuery query = newGetStocksQuery(productIds);
        GetStocksView view = (GetStocksView) queryGateway.execute(query);
        return new ResponseEntity<>(view, HttpStatus.OK);
    }

    private GetStocksQuery newGetStocksQuery(List<String> productIds) {
        GetStocksQuery query = new GetStocksQuery();
        query.setProductIds(productIds);
        return query;
    }

    @PostMapping("/api/v1/stocks")
    public ResponseEntity<Void> purchaseProduct(@Valid @RequestBody PurchaseProductDto dto) {
        PurchaseProductCommand command = newPurchaseProductCommand(dto);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private PurchaseProductCommand newPurchaseProductCommand(PurchaseProductDto dto) {
        PurchaseProductCommand command = new PurchaseProductCommand();
        command.setProductId(dto.getProductId());
        command.setQuantity(dto.getQuantity());
        return command;
    }
}
