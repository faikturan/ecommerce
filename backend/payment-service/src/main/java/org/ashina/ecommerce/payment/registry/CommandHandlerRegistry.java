package org.ashina.ecommerce.payment.registry;

import org.ashina.ecommerce.payment.application.command.handler.CreatePaymentCommandHandler;
import org.ashina.ecommerce.payment.infrastructure.persistence.repository.PaymentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerRegistry {

    @Bean
    public CreatePaymentCommandHandler createPurchaseCommandHandler(PaymentRepository paymentRepository) {
        return new CreatePaymentCommandHandler(paymentRepository);
    }
}
