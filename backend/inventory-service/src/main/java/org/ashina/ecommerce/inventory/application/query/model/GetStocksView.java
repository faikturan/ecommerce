package org.ashina.ecommerce.inventory.application.query.model;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetStocksView {

    @Data
    public static class Stock {
        private String productId;
        private Integer quantity;

        public Stock(org.ashina.ecommerce.inventory.domain.Stock domainStock) {
            this.productId = domainStock.getProductId();
            this.quantity = domainStock.getQuantity();
        }
    }

    private List<Stock> stocks;

    public GetStocksView(List<org.ashina.ecommerce.inventory.domain.Stock> domainStocks) {
        this.stocks = domainStocks
                .stream()
                .map(Stock::new)
                .collect(Collectors.toList());
    }
}
