package org.ashina.ecommerce.order.infrastructure.ecommerce.feign.fallback;

import feign.hystrix.FallbackFactory;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.client.CartClient;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model.GetCartDto;
import org.springframework.stereotype.Component;

@Component
public class GetCartFallbackFactory implements FallbackFactory<CartClient> {

    @Override
    public CartClient create(Throwable cause) {

        return new CartClient() {

            @Override
            public GetCartDto getCart() {
                return new GetCartDto();
            }
        };
    }
}
