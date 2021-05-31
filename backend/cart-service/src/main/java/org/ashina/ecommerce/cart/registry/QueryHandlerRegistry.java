package org.ashina.ecommerce.cart.registry;

import org.ashina.ecommerce.cart.application.query.handler.GetCartQueryHandler;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.CatalogService;
import org.ashina.ecommerce.cart.infrastructure.persistence.repository.CartRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryHandlerRegistry {

    @Bean
    public GetCartQueryHandler getCartQueryHandler(CartRepository cartRepository,
                                                   CatalogService catalogService) {
        return new GetCartQueryHandler(cartRepository, catalogService);
    }
}
