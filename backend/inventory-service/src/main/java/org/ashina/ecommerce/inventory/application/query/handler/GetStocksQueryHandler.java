package org.ashina.ecommerce.inventory.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.inventory.application.query.model.GetStocksQuery;
import org.ashina.ecommerce.inventory.application.query.model.GetStocksView;
import org.ashina.ecommerce.inventory.domain.Stock;
import org.ashina.ecommerce.inventory.infrastructure.persistence.repository.StockRepository;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;

import java.util.List;

@RequiredArgsConstructor
public class GetStocksQueryHandler implements QueryHandler<GetStocksQuery, GetStocksView> {

    private final StockRepository stockRepository;

    @Override
    public Class<?> support() {
        return GetStocksQuery.class;
    }

    @Override
    public GetStocksView handle(GetStocksQuery query) {
        List<Stock> stocks = stockRepository.findByProductIdIn(query.getProductIds());
        return new GetStocksView(stocks);
    }
}
