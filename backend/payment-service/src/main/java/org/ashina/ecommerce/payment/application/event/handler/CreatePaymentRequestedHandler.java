package org.ashina.ecommerce.payment.application.event.handler;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.ashina.ecommerce.payment.domain.Payment;
import org.ashina.ecommerce.payment.infrastructure.event.publisher.CreatePaymentRepliedPublisher;
import org.ashina.ecommerce.payment.infrastructure.persistence.repository.PaymentRepository;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.ashina.ecommerce.sharedkernel.event.handler.DomainEventHandler;
import org.ashina.ecommerce.sharedkernel.event.model.payment.CreatePaymentReplied;
import org.ashina.ecommerce.sharedkernel.event.model.payment.CreatePaymentRequested;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreatePaymentRequestedHandler implements DomainEventHandler<CreatePaymentRequested> {

    private final PaymentRepository paymentRepository;
    private final CreatePaymentRepliedPublisher createPaymentRepliedPublisher;

    @Override
    @Transactional
    public void handle(CreatePaymentRequested requestedEvent) {
        CreatePaymentReplied repliedEvent = new CreatePaymentReplied();
        repliedEvent.setTransactionId(requestedEvent.getTransactionId());
        try {
            // Create new payment
            Payment payment = newPayment(requestedEvent);
            paymentRepository.save(payment);

            // Reply request
            repliedEvent.setDone(true);
            createPaymentRepliedPublisher.publish(repliedEvent);
        } catch (Exception e) {
            // Reply request
            repliedEvent.setDone(false);
            repliedEvent.setThrowable(ExceptionUtils.getStackTrace(e));
            createPaymentRepliedPublisher.publish(repliedEvent);
            throw e;
        }
    }

    private Payment newPayment(CreatePaymentRequested event) {
        Payment payment = new Payment();
        payment.setId(DomainEntityIdentifierGenerator.uuid());
        payment.setOrderId(event.getOrderId());
        payment.setAmount(event.getAmount());
        return payment;
    }
}
