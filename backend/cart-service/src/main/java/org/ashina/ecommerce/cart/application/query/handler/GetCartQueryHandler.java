package org.ashina.ecommerce.cart.application.query.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetCartQueryHandler implements QueryHandler<GetCartQuery, GetCartView> {

    private final CartRepository cartRepository;
    private final ProductClient productClient;

    @Override
    public Class<?> support() {
        return GetCartQuery.class;
    }

    @Override
    @CircuitBreaker(name = "mainService", fallbackMethod="handleFallback")
    public GetCartView handle(GetCartQuery query) {
        GetCartView view = new GetCartView();

        // Get cart lines
        Optional<Cart> cartOpt = cartRepository.findByCustomerId(query.getCustomerId());
        if (!cartOpt.isPresent()) {
            return view;
        }
        Cart cart = cartOpt.get();
        if (CollectionUtils.isEmpty(cart.getLines())) {
            return view;
        }

        // Get products
        List<String> productIds = cart.getLines()
                .stream()
                .map(Cart.Line::getProductId)
                .collect(Collectors.toList());
        GetProductsDto getProductsDto = productClient.getProducts(productIds);
        if (CollectionUtils.isEmpty(getProductsDto.getProducts())) {
            return view;
        }

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

    public GetCartView handleFallback(Exception e) {
        log.error("Get cart failed", e);
        return new GetCartView();
    }
}
