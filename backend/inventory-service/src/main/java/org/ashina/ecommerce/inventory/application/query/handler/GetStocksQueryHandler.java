package org.ashina.ecommerce.inventory.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.inventory.application.query.model.GetStocksQuery;
import org.ashina.ecommerce.inventory.application.query.model.GetStocksView;
import org.ashina.ecommerce.inventory.domain.Stock;
import org.ashina.ecommerce.inventory.infrastructure.persistence.StockPersistence;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;
import org.ashina.ecommerce.sharedkernel.query.model.Query;

import java.util.List;

@RequiredArgsConstructor
public class GetStocksQueryHandler implements QueryHandler<GetStocksQuery, GetStocksView> {

    private final StockPersistence stockPersistence;

    @Override
    public Class<? extends Query> support() {
        return GetStocksQuery.class;
    }

    @Override
    public GetStocksView handle(GetStocksQuery query) {
        List<Stock> stocks = stockPersistence.findByProductIdIn(query.getProductIds());
        return new GetStocksView(stocks);
    }
}
