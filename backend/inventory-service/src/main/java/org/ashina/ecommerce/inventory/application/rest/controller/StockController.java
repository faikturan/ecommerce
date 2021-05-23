package org.ashina.ecommerce.inventory.application.rest.controller;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.inventory.application.query.model.GetStocksQuery;
import org.ashina.ecommerce.inventory.application.query.model.GetStocksView;
import org.ashina.ecommerce.sharedkernel.query.gateway.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final QueryGateway queryGateway;

    @GetMapping("/api/v1/stocks")
    public ResponseEntity<GetStocksView> getStocks(@RequestParam List<String> productIds) {
        GetStocksQuery query = newGetStocksQuery(productIds);
        GetStocksView view = (GetStocksView) queryGateway.execute(query);
        return new ResponseEntity<>(view, HttpStatus.OK);
    }

    private GetStocksQuery newGetStocksQuery(List<String> productIds) {
        GetStocksQuery query = new GetStocksQuery();
        query.setProductIds(productIds);
        query.setHasValidate(true);
        return query;
    }
}
