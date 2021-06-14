package org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.fallback;

import feign.hystrix.FallbackFactory;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.client.ProductClient;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.model.GetProductsDto;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class GetProductsFallbackFactory implements FallbackFactory<ProductClient> {

    @Override
    public ProductClient create(Throwable throwable) {

        return new ProductClient() {
            @Override
            public GetProductsDto getProducts(Collection<String> ids) {
                return new GetProductsDto();
            }
        };
    }
}
