package org.ashina.ecommerce.cart.registry;

import org.ashina.ecommerce.cart.application.command.handler.AddProductToCartCommandHandler;
import org.ashina.ecommerce.cart.application.command.handler.DeleteCartLineCommandHandler;
import org.ashina.ecommerce.cart.application.command.handler.UpdateCartLineCommandHandler;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.CatalogService;
import org.ashina.ecommerce.cart.infrastructure.persistence.repository.CartRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerRegistry {

    @Bean
    public AddProductToCartCommandHandler addProductToCartCommandHandler(CartRepository cartRepository,
                                                                         CatalogService catalogService) {
        return new AddProductToCartCommandHandler(cartRepository, catalogService);
    }

    @Bean
    public UpdateCartLineCommandHandler updateCartLineCommandHandler(CartRepository cartRepository) {
        return new UpdateCartLineCommandHandler(cartRepository);
    }

    @Bean
    public DeleteCartLineCommandHandler deleteCartLineCommandHandler(CartRepository cartRepository) {
        return new DeleteCartLineCommandHandler(cartRepository);
    }
}
