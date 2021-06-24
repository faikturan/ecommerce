package org.ashina.ecommerce.payment.application.command.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.payment.application.command.model.ProcessPaymentCommand;
import org.ashina.ecommerce.payment.domain.Payment;
import org.ashina.ecommerce.payment.infrastructure.persistence.repository.PaymentRepository;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessPaymentCommandHandler implements CommandHandler<ProcessPaymentCommand, Void> {

    private final PaymentRepository paymentRepository;

    @Override
    public Class<?> support() {
        return ProcessPaymentCommand.class;
    }

    @Override
    public Void handle(ProcessPaymentCommand command) {
        // Simulate processing payment
        processPayment(command);

        // Save payment
        Payment payment = newPayment(command);
        paymentRepository.save(payment);

        return null;
    }

    private void processPayment(ProcessPaymentCommand command) {
        log.info("Start processing payment of order {}", command.getOrderId());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Finish processing payment of order {}", command.getOrderId());
    }

    private Payment newPayment(ProcessPaymentCommand command) {
        Payment payment = new Payment();
        payment.setCustomerId(command.getCustomerId());
        payment.setOrderId(command.getOrderId());
        payment.setAmount(command.getAmount());
        return payment;
    }
}
