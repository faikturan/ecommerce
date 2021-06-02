package org.ashina.ecommerce.cart.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.cart.application.error.ErrorCode;
import org.ashina.ecommerce.cart.application.error.ServiceException;
import org.ashina.ecommerce.cart.application.query.model.GetCartQuery;
import org.ashina.ecommerce.cart.application.query.model.GetCartView;
import org.ashina.ecommerce.cart.domain.Cart;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.client.ProductClient;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.model.GetProductsDto;
import org.ashina.ecommerce.cart.infrastructure.persistence.repository.CartRepository;
import org.ashina.ecommerce.sharedkernel.query.handler.QueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetCartQueryHandler implements QueryHandler<GetCartQuery, GetCartView> {

    private final CartRepository cartRepository;
    private final ProductClient productClient;

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
        GetProductsDto getProductsDto = productClient.getProducts(productIds);

        // Process each cart line
        int total = 0;
        for (Cart.Line line : cart.getLines()) {
            GetProductsDto.Product product = getProductsDto.getProducts().stream()
                    .filter(it -> it.getId().equals(line.getProductId()))
                    .findAny()
                    .orElseThrow(() -> ServiceException.of(
                            ErrorCode.PRODUCT_NOT_FOUND,
                            String.format("Product %s not found", line.getProductId()),
                            HttpStatus.NOT_FOUND
                    ));
            view.addLine(new GetCartView.Line(line, product));
            total += product.getPrice() * line.getQuantity();
        }
        view.setTotal(total);

        return view;
    }

}
