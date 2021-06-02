package org.ashina.ecommerce.payment.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.payment.application.command.model.CreatePaymentCommand;
import org.ashina.ecommerce.payment.domain.Payment;
import org.ashina.ecommerce.payment.infrastructure.persistence.repository.PaymentRepository;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreatePaymentCommandHandler implements CommandHandler<CreatePaymentCommand, Void> {

    private final PaymentRepository paymentRepository;

    @Override
    public Class<?> support() {
        return CreatePaymentCommand.class;
    }

    @Override
    @Transactional
    public Void handle(CreatePaymentCommand command) {
        // Save payment
        Payment payment = newPayment(command);
        paymentRepository.save(payment);

        return null;
    }

    private Payment newPayment(CreatePaymentCommand command) {
        Payment payment = new Payment();
        payment.setId(DomainEntityIdentifierGenerator.uuid());
        payment.setOrderId(command.getOrderId());
        payment.setAmount(command.getAmount());
        return payment;
    }
}
