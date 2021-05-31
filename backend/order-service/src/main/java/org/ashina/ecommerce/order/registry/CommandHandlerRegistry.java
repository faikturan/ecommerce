package org.ashina.ecommerce.order.registry;

import org.ashina.ecommerce.order.application.command.handler.FulfillmentOrderCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerRegistry {

    @Bean
    public FulfillmentOrderCommandHandler createOrderCommandHandler(CartLinePersistence cartLinePersistence,
                                                                    CartService catalogService) {
        return new FulfillmentOrderCommandHandler(cartLinePersistence, catalogService);
    }

    @Bean
    public UpdateCartLineCommandHandler updateCartLineCommandHandler(CartLinePersistence cartLinePersistence) {
        return new UpdateCartLineCommandHandler(cartLinePersistence);
    }

    @Bean
    public DeleteCartLineCommandHandler deleteCartLineCommandHandler(CartLinePersistence cartLinePersistence) {
        return new DeleteCartLineCommandHandler(cartLinePersistence);
    }
}
