package org.ashina.ecommerce.inventory.application.event.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.inventory.domain.Stock;
import org.ashina.ecommerce.inventory.infrastructure.persistence.StockPersistence;
import org.ashina.ecommerce.sharedkernel.exception.DomainException;
import org.ashina.ecommerce.sharedkernel.event.handler.DomainEventHandler;
import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCreated;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderCreatedHandler implements DomainEventHandler<OrderCreated> {

    private final StockPersistence stockPersistence;

    @Override
    public void handle(OrderCreated event) throws DomainException {
        Map<String, Integer> lineMap = event.getLines()
                .stream()
                .collect(Collectors.toMap(OrderCreated.Line::getProductId, OrderCreated.Line::getQuantity));
        Set<String> productIds = lineMap.keySet();
        List<Stock> stocks = stockPersistence.findByProductIdIn(productIds);
        stocks.forEach(stock -> stock.setQuantity(stock.getQuantity() - lineMap.get(stock.getProductId())));
        stockPersistence.saveAll(stocks);
    }
}
