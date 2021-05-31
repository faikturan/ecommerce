package org.ashina.ecommerce.cart.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.cart.application.error.ErrorCode;
import org.ashina.ecommerce.cart.application.error.ServiceException;
import org.ashina.ecommerce.cart.application.query.model.GetCartQuery;
import org.ashina.ecommerce.cart.application.query.model.GetCartView;
import org.ashina.ecommerce.cart.domain.Cart;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.CatalogService;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.model.Product;
import org.ashina.ecommerce.cart.infrastructure.persistence.repository.CartRepository;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetCartQueryHandler implements QueryHandler<GetCartQuery, GetCartView> {

    private final CartRepository cartRepository;
    private final CatalogService catalogService;

    @Override
    public Class<?> support() {
        return GetCartQuery.class;
    }

    @Override
    public GetCartView handle(GetCartQuery query) {
        GetCartView view = new GetCartView();

        // Get cart lines
        Cart cart = cartRepository.findByCustomerId(query.getCustomerId())
                .orElseThrow(() -> ServiceException.of(
                   ErrorCode.CART_NOT_FOUND,
                   String.format("Cart of customer %s not found", query.getCustomerId()),
                   HttpStatus.NOT_FOUND
                ));
        if (CollectionUtils.isEmpty(cart.getLines())) {
            return view;
        }

        // Get products
        List<String> productIds = cart.getLines()
                .stream()
                .map(Cart.Line::getProductId)
                .collect(Collectors.toList());
        Map<String, Product> productMap = catalogService.getProducts(productIds);

        // Process each cart line
        int total = 0;
        for (Cart.Line line : cart.getLines()) {
            Product product = productMap.get(line.getProductId());
            if (product == null) {
                throw ServiceException.of(
                        ErrorCode.PRODUCT_NOT_FOUND,
                        String.format("Product %s not found", line.getProductId()),
                        HttpStatus.NOT_FOUND
                );
            }
            view.addLine(new GetCartView.Line(line, product));
            total += product.getPrice() * line.getQuantity();
        }
        view.setTotal(total);

        return view;
    }

}
