package org.ashina.ecommerce.order.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.order.application.error.ErrorCode;
import org.ashina.ecommerce.order.application.query.model.GetCartQuery;
import org.ashina.ecommerce.order.application.query.model.GetCartView;
import org.ashina.ecommerce.order.domain.CartLine;
import org.ashina.ecommerce.order.infrastructure.ecommerce.CatalogService;
import org.ashina.ecommerce.order.infrastructure.ecommerce.model.Product;
import org.ashina.ecommerce.order.infrastructure.persistence.CartLinePersistence;
import org.ashina.ecommerce.sharedkernel.exception.DomainException;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;
import org.ashina.ecommerce.sharedkernel.query.model.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetCartQueryHandler implements QueryHandler<GetCartQuery, GetCartView> {

    private final CartLinePersistence cartLinePersistence;
    private final CatalogService catalogService;

    @Override
    public Class<? extends Query> support() {
        return GetCartQuery.class;
    }

    @Override
    public GetCartView handle(GetCartQuery query) throws DomainException {
        GetCartView view = new GetCartView();

        // Get cart lines
        List<CartLine> cartLines = cartLinePersistence.findByCustomerId(query.getCustomerId());
        if (CollectionUtils.isEmpty(cartLines)) {
            return view;
        }

        // Get products
        List<String> productIds = cartLines
                .stream()
                .map(CartLine::getProductId)
                .collect(Collectors.toList());
        Map<String, Product> productMap = catalogService.getProducts(productIds);

        // Total
        int total = 0;

        // Process each cart line
        for (CartLine cartLine : cartLines) {
            Product product = productMap.get(cartLine.getProductId());
            if (product == null) {
                throw new DomainException(
                        ErrorCode.PRODUCT_NOT_FOUND,
                        String.format("Product %s not found", cartLine.getProductId())
                );
            }
            view.addLine(new GetCartView.Line(cartLine, product));
            total += product.getPrice() * cartLine.getQuantity();
        }

        view.setTotal(total);

        return view;
    }

}
