package org.ashina.ecommerce.order.registry;

import org.ashina.ecommerce.order.application.command.handler.CreateOrderCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerRegistry {

    @Bean
    public CreateOrderCommandHandler createOrderCommandHandler(CartLinePersistence cartLinePersistence,
                                                                    CartService catalogService) {
        return new CreateOrderCommandHandler(cartLinePersistence, catalogService);
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
